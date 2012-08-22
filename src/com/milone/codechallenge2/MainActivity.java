package com.milone.codechallenge2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		TextView txtResults = (TextView) findViewById(R.id.TextView);

		try {
			// Load the file from the external storage root folder
			// typically the sd card, but some devices have this partition
			// built in. It's whatever the root folder is when you
			// use USB Mass Storage on your device.
			File sdcard = Environment.getExternalStorageDirectory();
			File file = new File(sdcard, "triangle.txt");
			BufferedReader br = new BufferedReader(new FileReader(file));

			String line = null;

			// First find out how high the triangle is
			int triangleheight = 0;
			while ((line = br.readLine()) != null)
				triangleheight++;

			// Close and reopen the file to start over the read
			br.close();
			br = new BufferedReader(new FileReader(file));

			// Load the entire Triangle into an array
			int[][] triangle;
			triangle = new int[triangleheight][triangleheight];
			int row = 0;
			int pos = 0;
			while ((line = br.readLine()) != null) {
				for (String token : line.split(" ")) {
					triangle[row][pos] = Integer.parseInt(token);
					pos++;
				}
				row++;
				pos = 0;
			}

			// All the data is in the array, close the text file
			br.close();

			// Perform sum calculations to find best route.

			// We work from the bottom up starting at the 2nd to last row.
			// View each number on the 2nd to last row's 2 adjacent
			// numbers from the following row to find the largest of the 2.

			// We now replace that current spot in the array with it's
			// original value + it's highest from 2 adjacent numbers compared.

			// After a whole row is complete, consider that the triangle is 
			//now 1 row shorter, with the sum of the bottom 2 rows now the new bottom row.

			// Repeat the process until we get to the top or array[0][0], which
			// now contains the biggest sum possible!

			for (row = triangleheight - 2; row >= 0; row--)
				for (pos = 0; pos <= row; pos++) {
					if (triangle[row + 1][pos] > triangle[row + 1][pos + 1])
						triangle[row][pos] += triangle[row + 1][pos];
					else
						triangle[row][pos] += triangle[row + 1][pos + 1];
				}

			// Display results
			txtResults.setText("The triangle in triangle.txt was " + triangleheight + " rows total and it's largest sum of adjacent numbers is " + triangle[0][0]);

		} catch (IOException e) {
			txtResults.setText("There was an error loading triangle.txt");
			e.printStackTrace();
		}

	}

}
