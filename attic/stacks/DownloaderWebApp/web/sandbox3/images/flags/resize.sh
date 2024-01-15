#!/bin/sh

rm -f *-small.png;

for i in *.png; do
    convert -resize 14x14 -sharpen 0.1 -quality 100 -background white $i ${i/./-small.};
done
