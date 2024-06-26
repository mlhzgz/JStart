package jstart;

/**
 * ANSI Colors definitions for Unix-type console
 */
public final class Colors {

    private Colors(){}

    /**
     * Reset the colors (fore and back-ground)
     */
    public static final String RESET = "\033[0m"; // Text Reset

    /**
     * Regular colors
     */
    public static final String BLACK = "\033[0;30m"; // BLACK
    public static final String RED = "\033[0;31m"; // RED
    public static final String GREEN = "\033[0;32m"; // GREEN
    public static final String YELLOW = "\033[0;33m"; // YELLOW
    public static final String BLUE = "\033[0;34m"; // BLUE
    public static final String PURPLE = "\033[0;35m"; // PURPLE
    public static final String CYAN = "\033[0;36m"; // CYAN
    public static final String WHITE = "\033[0;37m"; // WHITE

    /**
     * Bold regular colors
     */
    public static class Bold {
        public static final String BLACK = "\033[1;30m"; // BLACK
        public static final String RED = "\033[1;31m"; // RED
        public static final String GREEN = "\033[1;32m"; // GREEN
        public static final String YELLOW = "\033[1;33m"; // YELLOW
        public static final String BLUE = "\033[1;34m"; // BLUE
        public static final String PURPLE = "\033[1;35m"; // PURPLE
        public static final String CYAN = "\033[1;36m"; // CYAN
        public static final String WHITE = "\033[1;37m"; // WHITE

        /**
         * Bolder high colors
         */
        public static class High {
            public static final String BLACK = "\033[1;90m"; // BLACK
            public static final String RED = "\033[1;91m"; // RED
            public static final String GREEN = "\033[1;92m"; // GREEN
            public static final String YELLOW = "\033[1;93m";// YELLOW
            public static final String BLUE = "\033[1;94m"; // BLUE
            public static final String PURPLE = "\033[1;95m";// PURPLE
            public static final String CYAN = "\033[1;96m"; // CYAN
            public static final String WHITE = "\033[1;97m"; // WHITE

        }
    }

    /**
     * Underline layout
     */
    public static class Underline {
        public static final String BLACK = "\033[4;30m"; // BLACK
        public static final String RED = "\033[4;31m"; // RED
        public static final String GREEN = "\033[4;32m"; // GREEN
        public static final String YELLOW = "\033[4;33m"; // YELLOW
        public static final String BLUE = "\033[4;34m"; // BLUE
        public static final String PURPLE = "\033[4;35m"; // PURPLE
        public static final String CYAN = "\033[4;36m"; // CYAN
        public static final String WHITE = "\033[4;37m"; // WHITE

    }

    /**
     * Background layout
     */
    public static class Background {
        public static final String BLACK = "\033[40m"; // BLACK
        public static final String RED = "\033[41m"; // RED
        public static final String GREEN = "\033[42m"; // GREEN
        public static final String YELLOW = "\033[43m"; // YELLOW
        public static final String BLUE = "\033[44m"; // BLUE
        public static final String PURPLE = "\033[45m"; // PURPLE
        public static final String CYAN = "\033[46m"; // CYAN
        public static final String WHITE = "\033[47m"; // WHITE

        /**
         * Lighter background layout
         */
        public static class High {
            public static final String BLACK = "\033[0;100m";// BLACK
            public static final String RED = "\033[0;101m";// RED
            public static final String GREEN = "\033[0;102m";// GREEN
            public static final String YELLOW = "\033[0;103m";// YELLOW
            public static final String BLUE = "\033[0;104m";// BLUE
            public static final String PURPLE = "\033[0;105m"; // PURPLE
            public static final String CYAN = "\033[0;106m"; // CYAN
            public static final String WHITE = "\033[0;107m"; // WHITE

        }
    }

    /**
     * Lighter layout
     */
    public static class High {
        public static final String BLACK = "\033[0;90m"; // BLACK
        public static final String RED = "\033[0;91m"; // RED
        public static final String GREEN = "\033[0;92m"; // GREEN
        public static final String YELLOW = "\033[0;93m"; // YELLOW
        public static final String BLUE = "\033[0;94m"; // BLUE
        public static final String PURPLE = "\033[0;95m"; // PURPLE
        public static final String CYAN = "\033[0;96m"; // CYAN
        public static final String WHITE = "\033[0;97m"; // WHITE

    }
}