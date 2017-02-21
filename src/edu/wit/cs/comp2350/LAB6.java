package edu.wit.cs.comp2350;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;


/**
 * 
 * @author kreimendahl
 */

// Finds the longest common substring in two text files
public class LAB6 {

	private static int best_cost = 0;

	//TODO Document this method
	public static String[] FindSubstrDYN(String text1, String text2) {
		// TODO Implement this method
		return new String[]{"", ""};
	}
	
	private static String sol1, sol2;

	private static void dfs_solve(int i1, int i2, String s1, String s2, char[] out1, char[] out2, int score, int index) {
	  
	  if ((i1 >= s1.length()) && (i2 >= s2.length())) {
	    if (score > best_cost) {
	      out1[index] = '\0';
	      out2[index] = '\0';
	      best_cost = score;
	      sol1 = String.valueOf(out1).substring(0, String.valueOf(out1).indexOf('\0'));
	      sol2 = String.valueOf(out2).substring(0, String.valueOf(out2).indexOf('\0'));
	    }
	  }
	  else if ((i1 >= s1.length()) && (i2 < s2.length())) {
	    out1[index] = '-';
	    out2[index] = s2.charAt(i2);
	    dfs_solve(i1, i2 + 1, s1, s2, out1, out2, score, index+1);
	  }
	  else if ((i1 < s1.length()) && (i2 >= s2.length())) {
	    out1[index] = s1.charAt(i1);
	    out2[index] = '-';
	    dfs_solve(i1 + 1, i2, s1, s2, out1, out2, score, index+1);
	  }
	  else {
	    if (s1.charAt(i1) == s2.charAt(i2)) {
	      out1[index] = s1.charAt(i1);
	      out2[index] = s2.charAt(i2);
	      dfs_solve(i1 + 1, i2 + 1, s1, s2, out1, out2, score + 1, index + 1);
	    }
	    out1[index] = '-';
	    out2[index] = s2.charAt(i2);
	    dfs_solve(i1, i2 + 1, s1, s2, out1, out2, score, index + 1);
	    
	    out1[index] = s1.charAt(i1);
	    out2[index] = '-';
	    dfs_solve(i1 + 1, i2, s1, s2, out1, out2, score, index + 1);
	  }

	}

	public static String[] FindSubstrDFS(String text1, String text2) {
	  int max_len = text1.length() + text2.length() + 1;
	  char[] out1 = new char[max_len];
	  char[] out2 = new char[max_len];
	  
	  dfs_solve(0, 0, text1, text2, out1, out2, 0, 0);
	  
	  String[] ret = new String[2];
	  ret[0] = sol1; ret[1] = sol2;
	  return ret; 
	}	
	
	public static int getBest() {
		return best_cost;
	}
	
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		String file1, file2, text1 = "", text2 = "";
		System.out.printf("Enter <text1> <text2> <algorithm>, ([dfs] - depth first search, [dyn] - dynamic programming): ");
		file1 = s.next();
		file2 = s.next();
		
		try {
			text1 = new String(Files.readAllBytes(Paths.get(file1)));
			text2 = new String(Files.readAllBytes(Paths.get(file2)));
		} catch (IOException e) {
			System.err.println("Cannot open files " + file1 + " and " + file2 + ". Exiting.");
			System.exit(0);
		}
		
		String algo = s.next();
		String[] result = {""};
		
		switch (algo) {
		case "dfs":
			result = FindSubstrDFS(text1, text2);
			break;
		case "dyn":
			result = FindSubstrDYN(text1, text2);
			break;
		default:
			System.out.println("Invalid algorithm");
			System.exit(0);
			break;
		}
		
		s.close();
		
		System.out.printf("Best string alignment:\n%s\n%s\nBest cost: %d\n", result[0], result[1], best_cost);	
	}

}
