# acdepub - a simple xml to epub converter.

I bought a Kobo Mini ereader and got started on reading all the Victorian books in the world.
But i soon got fed up of the standard Gutenberg epub formatting, specifically the horrible 
default cover and the way that the Kobo would use a screenshot of the front page when no
cover image was included, said front page being the standard gutenberg legal boilerplate
reduced to unreadable size.

I looked at DocBook but it was massively complicated and still didn't do everything i needed.
So i decided to do it myself. This is the result.

It uses a text document marked up with some xml to define chapters and paragraphs and the odd
bit of meta data and generates an epub.

I use NetBeans on Mint 13 but the project is Maven based so it should be easy enough to use with other IDEs.

After building it i run it from the command line, something like this:
java -cp ./target/acdepub-1.2-SNAPSHOT-jar-with-dependencies.jar me/koogy/acdepub/Main five_jars.xml
which will create a five_jars.epub

I've been using it for a couple of years now but it's still a bit beta - I need to document this a bit
more, and add some markup examples etc. There are some in src/test/xml but it's come on a way since i wrote those.
