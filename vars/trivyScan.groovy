def containerVulnerabilities(String imageName) {
    sh """
        echo image: ${imageName}

        trivy image ${imageName} \
        --severity LOW,MEDIUM,HIGH \
        --exit-code 0 \
        --quiet \
        --format json -o trivy-lowmedhigh.json

        trivy image ${imageName} \
        --severity CRITICAL \
        --exit-code 1 \
        --quiet \
        --format json -o trivy-critical.json
    """
}

def reportsConversion() {
    sh '''
        trivy convert \
        --template "/usr/local/share/trivy/templates/html.tpl" \
        --output trivy-report-lowmedhigh.html trivy-lowmedhigh.json

        trivy convert \
        --template "/usr/local/share/trivy/templates/html.tpl" \
        --output trivy-report-critical.html trivy-critical.json

        trivy convert \
        --template "/usr/local/share/trivy/templates/junit.tpl" \
        --output trivy-report-lowmedhigh.xml trivy-lowmedhigh.json

        trivy convert \
        --template "/usr/local/share/trivy/templates/junit.tpl" \
        --output trivy-report-critical.xml trivy-critical.json 
    '''
}