package com.dyggyty.manipulation;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

/**
 * Main data manipulation application class.
 */
public class ManipulationApp {

    private static final ApplicationContext APPLICATION_CONTEXT = new GenericXmlApplicationContext(new ClassPathResource("/applicationContext.xml"));

    public static void main(String[] args) {

        if (args.length == 0) {
            printHelp();
            return;
        }

        APPLICATION_CONTEXT
                .getBean(Manipulation.class)
                .parseFolder(args[0], args.length > 1 ? args[1] : null);
    }

    private static void printHelp() {
        System.out.println("<folder name> [<ouput name>]");
    }
}
