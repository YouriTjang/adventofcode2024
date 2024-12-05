#!/bin/bash
if [ $# -eq 0 ]; then
    echo "No arguments provided. Add the day, like 06"
else
    echo $1
    cp -r "src/day00" "src/day$1"
    mv "src/day$1/Day00.kt" "src/day$1/Day$1.kt"
    sed -i '' "s/00/$1/g" "src/day$1/Day$1.kt"
    git add "src/day$1/Day$1.kt"
fi
