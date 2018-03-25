
package utilities;

import java.io.File;

import dandimrod.main.SpringGenerator;

public class GenerateAll {

	public static void main(final String[] args) {
		SpringGenerator.generateFromXMI(new File("\\\\VBOXSVR\\Workspaces\\Rep 12\\Acme-Survival\\src\\main\\java\\utilities\\hackaton.xml"), 3);
	}
}
