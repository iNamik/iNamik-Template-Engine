iNamik Template Engine
======================

**Template Engine in Java**

FEATURES
--------

1.	**Style**

	The templating style is similar to PHP's [Smarty Templates](http://smarty.net).

	The Engine supports the following tag identifiers:

	1. Single-Brace (smarty default):
	{ $foo }

	2. Double-Brace (javascript friendly):
	{{ $foo }}

	3. Angle-Percent (asp-like):
	<% $foo %>

2.	**Suitable for rending any textual content**

	* XML
	* HTML
	* Plain Text

3.	**Encourages well-formatted templates**

	* Works very hard to remove extra whitespace introduced by template tags
	* When consistent indentation is used, it is preserved
	* For example, this template snippet

	<pre>
	{* indentations due to well-formatted block tags will be removed *}
	{capture id='body'}
		{set $list=['one', 'two', 'three']}
		{foreach id=item in=$list loop=loop}
			{if $loop.first}
				# Start List
			{/if}
				{* This tab preserved *}
				[{$loop.iteration}] {$item}
			{if $loop.last}
				# End List
			{/if}
		{/}
	{/capture}
	{$body}
	</pre>

	Renders this well-formatted output

	<pre>
	\# Start List
		[1] one
		[2] two
		[3] three
	\# End List
	</pre>

4.	**Caching**

	The Engine supports [EHCache hi-performance cache](http://ehcache.org) for storage of tokenized templates.

	If you decide not to use the EHCache, the Engine will employ a temporary cache using a hashtable of weak references. This is used to ensure that included templates are only tokenized once when processing the main template.

5.	**Macros**

	You can capture a template (or portion of) in tokenized form and render it multiple times.

	<pre>
	{macro id='macro'}
		Hello, {$name}
	{/macro}
	{set name='foo'}
	{$macro}
	{set name='bar'}
	{$macro}
	</pre>


LEARN MORE
----------

* [A Simple Coding Example](http://github.com/iNamik/iNamik-Template-Engine/wiki/A-Simple-Coding-Example)
* [Standard tags, filters and functions](http://github.com/iNamik/iNamik-Template-Engine/wiki/iNamik-Main-Template-Library-Config)
* [Configuring the template engine](http://github.com/iNamik/iNamik-Template-Engine/wiki/Sample-Template-Engine-Config)
* [Creating your own tag library](http://github.com/iNamik/iNamik-Template-Engine/wiki/Sample-Template-Library-Config)


REQUIREMENTS
------------

1.	**Jars needed to build &amp; run the Engine**

	* Activation (1.1.1)
	* ANTLR (2.7.7)
	* Commons BeanUtils (1.8.3) Collections
	* Commons Beanutils (1.8.3) Core
	* Commons Logging (1.1.1)
	* EHCache (2.2.0) Core
	* JAXB API (2.2.1 20100511)
	* JAXB IMPL (2.2.1 20100511)

	**NOTE:** When building from source, these jars should be placed into a folder named

	*/&nbsp;path&nbsp;/&nbsp;to&nbsp;/&nbsp;distribution&nbsp;/&nbsp;lib&nbsp;/*

2.	**Jars needed just for building**

	* JAXB XJC (2.2.1 20100511)

	**NOTE:** When building from source, this jar should be placed into a folder named

	*/&nbsp;path&nbsp;/&nbsp;to&nbsp;/&nbsp;distribution&nbsp;/&nbsp;lib-build&nbsp;/*


DOWNLOAD
--------

* View the source code on [GitHub](https://github.com/iNamik/iNamik-Template-Engine)
* Download [Source/Binary Distributions](https://github.com/iNamik/iNamik-Template-Engine/downloads)


AUTHORS
-------

 * David Farrell
