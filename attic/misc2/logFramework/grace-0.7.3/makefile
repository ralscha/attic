RELEASE_ITEMS = docs makefile						\
    classes/{makefile,Header.java}					\
    classes/grace/{makefile,run-tests} 					\
    classes/grace/{log,io,util,tools,test}

ifeq "$(RELEASE)" ""
    RELEASE = $(shell date +'%y:%m:%d:%H:%M:%S')
else
endif
RELEASE_DIR = release/$(RELEASE)

all:: 
	cd classes; make
	cd docs/api; make
clean::
	cd classes; make clean
	cd docs/api; make clean

release:: 
	if [ ! -d $(RELEASE_DIR) ]; then 				\
	    mkdir --parents $(RELEASE_DIR); 				\
	fi
	tar cf - --exclude=classes/grace/log/viewer\* $(RELEASE_ITEMS) 	\
	    | (cd $(RELEASE_DIR); tar xf -);
	cd $(RELEASE_DIR);						\
	find . -name '*~' -exec rm {} \;;				\
	for file in `find . -name \*.java -print`; do			\
	    cat classes/Header.java $$file > $$file.tmp;		\
	    mv $$file.tmp $$file;					\
	done;								\
	echo make clean;						\
	echo make;							\
	if [ ! -d lib ]; then mkdir lib; fi;				\
	(cd classes; jar c0f ../lib/grace.jar `find . -name '*.class'`);\
	cd ..;								\
	pwd;\
	cp $(RELEASE)/docs/{license.html,download.html} .;		\
	jar cf $(RELEASE).jar $(RELEASE);				\
	mv $(RELEASE).jar $(RELEASE)/docs;				\
	cd $(RELEASE);							\
	rm -rf classes makefile;					\
	mv docs/* .;							\
	rmdir docs;


