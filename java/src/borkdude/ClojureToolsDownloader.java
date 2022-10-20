package borkdude;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Paths;


/**
 * A test jar implementation useful for testing jvm options and
 * parameter passing of the jar wrapper script.
 */
public class ClojureToolsDownloader
{
    private final static String srcFormat = "https://download.clojure.org/install/clojure-tools-%s.zip";
    /**
     *
     */
    public static void main (String[] args)
    {
	int exit = 0;

	if (args.length != 2)
	    {
		System.err.println("ClojureToolsDownloader error: Please supply both clojure-tools version and download directory.");
		exit = 1;
	    }
	else
	    {
		String version = args[0];
		String destDir = args[1];

		if (!Files.isDirectory(Paths.get(destDir)))
		    {
			System.err.println("ClojureToolsDownloader error: Destination directory not found: " + destDir);
			exit = 2;
		    }
		else
		    {
			try {
			    String src = String.format(srcFormat, version);
			    URL url = new URL(src);

			    System.out.println(":ClojureToolsDownloader :downloading-to... " + destDir);
			    ReadableByteChannel readableByteChannel = Channels.newChannel(url.openStream());
			    
			    String destFile = Paths.get(destDir, "tools.zip").toString();
			    System.out.println(":ClojureToolsDownloader saving-as... " + destFile); 
			    FileOutputStream fileOutputStream = new FileOutputStream(destFile);
			    FileChannel fileChannel = fileOutputStream.getChannel();
			    
			    fileOutputStream.getChannel()
				.transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
			} catch (IOException e)
			    {
				e.printStackTrace();
				exit = 3;
			    }
		    }
	    }
	System.exit(exit);
    }
}
