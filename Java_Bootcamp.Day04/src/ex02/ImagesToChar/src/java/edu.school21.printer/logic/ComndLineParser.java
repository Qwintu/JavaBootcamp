package edu.school21.printer.logic;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(separators = "=")

public class ComndLineParser {
    @Parameter(names = {"-w", "--white"}, description = "Color to draw white pix", required = true)
    private String white = "WHITE";

    @Parameter(names = {"-b", "--black"}, description = "Color to draw black pix", required = true)
    private String black = "BLACK";

    public String getWhite() {
        return white;
    }

    public String getBlack() {
        return black;
    }
}
