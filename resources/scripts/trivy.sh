#!/bin/bash

echo imageName - $1
echo severity - $2
echo exitCode - $3

trivy image $1 \
    --severity $2 \
    --exit-code $3 \
    --quiet \
    --format json -o trivy-$2-report.json
