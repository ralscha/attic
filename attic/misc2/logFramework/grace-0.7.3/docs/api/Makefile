#GRACE_IMAGE="<IMG SRC=../../logo.jpg HEIGHT=61 WIDTH=200>" 

ifneq "$(GRACE_IMAGE)" ""
FLAGS=-protected -header $(GRACE_IMAGE)
else
FLAGS=-J-Xmx128m -protected
endif

SRC = ../../classes
DIRS = grace/log grace/util grace/io grace/tools

PACKAGES=grace.log grace.util grace.io \
    grace.log.test grace.log.test.centralized grace.log.test.handlers \
    grace.log.test.performance grace.log.test.performance

all::
	cwd=$(PWD);							\
	cd $(SRC);							\
	for dir in $(DIRS); do						\
	    for checkdir in `find $$dir -type d ! -name CVS`; do	\
		if ls $$checkdir/*.java > /dev/null 2>&1; then		\
		    packages="$$packages $$checkdir";			\
		fi;							\
	    done;							\
	done;								\
	packages=`echo $$packages | sed 's:/:.:g'`;			\
	echo $$packages;						\
	cd $$cwd;							\
	/usr/local/packages/jdk1.2/bin/javadoc $(FLAGS) $$packages;
clean::
	rm *.html package-list *.css;
	rm -rf grace;
	


