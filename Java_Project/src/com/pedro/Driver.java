package com.pedro;

import java.util.Scanner;

public class Driver {
	
	static Scanner input = new Scanner(System.in);

	public static void main(String[] args) {
		
		System.out.print("How many zones will this system have?");
		String zoneCount;
		Boolean vetted = false;
		while (!vetted) {
			try {
				zoneCount = input.nextLine();
				Integer.parseInt(zoneCount);
				vetted = true;
			}
			catch(NumberFormatException e){
				System.out.println("please enter a whole number");
			}
		}
	}
}
