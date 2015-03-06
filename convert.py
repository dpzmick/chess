import sys
import os

h = 64
w = 64

for fname in sys.argv[1:]:
    splitted = fname.split('/')
    new_path = "icons/" + splitted[1] + "/" + splitted[2].split(".")[0] + ".png"
    command = "rsvg-convert -w %d -w %d %s -o %s" % (h, w, fname, new_path)
    os.system(command)
