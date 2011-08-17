$Id: README.txt,v 1.2 2008-02-22 03:02:06 dave Exp $

build.xml references two lib folders, 'lib' and 'lib-build'. 'lib' stores 
runtime/build-time jars and 'lib-build' stores build-time-only jars.  If these 
folders do not exist in your source distribution, please create them.

Here is a list of jar files and versions that TiTE is developed against:

lib/
	Activation (1.1.1)
	ANTLR (2.7.7)
	Commons BeanUtils (1.8.3) Collections
	Commons Beanutils (1.8.3) Core
	Commons Logging (1.1.1)
	EHCache (2.2.0) Core
	JAXB API  (2.2.1 20100511)
	JAXB IMPL (2.2.1 20100511)

/lib-build/
	JAXB XJC (2.2.1 20100511)
