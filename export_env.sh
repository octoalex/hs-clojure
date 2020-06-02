#!/bin/bash

set -e
set -u

pwd=$(pwd)

input="$pwd/.env"
while IFS= read -r line
do
  echo "EXPORT $line"
done < "$input"