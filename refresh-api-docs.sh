#!/bin/bash

rm -rf api-docs

mkdir -p api-docs
cd api-docs

wget -r --no-parent --no-check-certificate https://www.linode.com/api/
