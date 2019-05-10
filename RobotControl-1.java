import javax.swing.JOptionPane;

class RobotControl
{
	private Robot r;
	public static StringBuilder sb;

	// Examples of constants
	private final int SOURCE_LOCATION = 10;
	private final int TARGET_1 = 1;
	private final int TARGET_2 = 2;
	private final int FIRST_BAR_POSITION = 3;

	public RobotControl(Robot r)
	{
		this.r = r;
	}

	public void control(int barHeights[], int blockHeights[])
	{
		sampleControlMechanism(barHeights, blockHeights);
	}

	public void sampleControlMechanism(int barHeights[], int blockHeights[])
	{
		
		// Internally the Robot object maintains the value for Robot height(h),
		// arm-width (w) and picker-depth (d).

		// These values are displayed for your convenience
		// These values are initialised as h=2 w=1 and d=0

		// When you call the methods up() or down() h will be changed
		// When you call the methods extend() or contract() w will be changed
		// When you call the methods lower() or raise() d will be changed

		// sample code to get you started
		// Try running this program with obstacle 555555 and blocks of height
		// 2222 (default)
		// It will work for fisrt block only
		// You are free to introduce any other variables

		int h = 2; // Initial height of arm 1
		int w = 1; // Initial width of arm 2
		int d = 0; // Initial depth of arm 3

		
		int sourceHt = 0;
		for(int i = 0 ;i<blockHeights.length;i++)
		{
			sourceHt += blockHeights[i];
		}

		// For Parts (a) and (b) assume all four blocks are of the same height
		// For Part (c) you need to compute this from the values stored in the
		// array blockHeights
		// i.e. sourceHt = blockHeights[0] + blockHeights[1] + ... use a loop!

		int targetCol1Ht = 0; // Applicable only for part (c) - Initially empty
		int targetCol2Ht = 0; // Applicable only for part (c) - Initially empty

		// height of block just picked will be 3 for parts A and B
		// For part (c) this value must be extracted from the topmost unused value
		// from the array blockHeights
		int currentBar = FIRST_BAR_POSITION;
		//int blockHt = blockHeights[currentBar];

		// clearance should be based on the bars, the blocks placed on them,
		// the height of source blocks and the height of current block

		// Initially clearance will be determined by the blocks at source
		// (3+3+3+3=12)
		// as they are higher than any bar and block-height combined
		
		int clearence = sourceHt;

		// Raise it high enough - assumed max obstacle = 4 < sourceHt

		// this makes sure robot goes high enough to clear any obstacles
		
		
		while (h < clearence + 1)
		{
			// Raising 1
			r.up();

			// Current height of arm1 being incremented by 1
			h++;
		}

		System.out.println("Debug 1: height(arm1)= " + h + " width (arm2) = "
				+ w + " depth (arm3) =" + d);

		// this will need to be updated each time a block is dropped off
		//int extendAmt = 10;
		// why not see the effect of changing contractAmt to 6 ?
					int contractAmt = 7;
					// barHeights



		for(int i =blockHeights.length-1 ;i>=0;i--)
		{
			/*if(sourceHt <10)
			{
				while (h > clearence -1)
				{
					// Raising 1
					r.down();

					// Current height of arm1 being incremented by 1
					h--;
				}

			}*/
			System.out.println("weight"+w);
			// Bring arm 2 to column 10
			while (w < SOURCE_LOCATION)
			{
				// moving 1 step horizontally
				r.extend();

				// Current width of arm2 being incremented by 1
				w++;
			}
			System.out.println("Debug 2: height(arm1)= " + h + " width (arm2) = "
					+ w + " depth (arm3) =" + d);

			// lowering third arm - the amount to lower is based on current height
			// and the top of source blocks

			// the position of the picker (bottom of third arm) is determined by h
			// and d
			while (h - d > sourceHt + 1)
			{
				// lowering third arm
				r.lower();

				// current depth of arm 3 being incremented
				d++;
			}
			// picking the topmost block
			r.pick();
		
			// topmost block is assumed to be 3 for parts (a) and (b)
			
		
			// When you pick the top block height of source decreases
			sourceHt -= blockHeights[i];
			
			// raising third arm all the way until d becomes 0
			while (d > 0)
			{
				r.raise();
				d--;
			}
		
			System.out.println("Debug 3: height(arm1)= " + h + " width (arm2) = "
					+ w + " depth (arm3) =" + d);
		
			
		
			// Must be a variable. Initially contract by 3 units to get to column 3
			// where the first bar is placed (from column 10)
			System.out.println(blockHeights[i] +" fgf" +w);
			if(blockHeights[i] == 1)
			{
				contractAmt =  TARGET_1;
			}
			else if(blockHeights[i] == 2)
			{
				contractAmt =  TARGET_2;
			}
			else
			{
				contractAmt =  currentBar;
			}
			System.out.println(contractAmt+""+w);
			while (contractAmt < w)
			{
				r.contract();
				//contractAmt--;
				w--;
			}
			System.out.println("Debug 4: height(arm1)= " + h + " width (arm2) = "
					+ w + " depth (arm3) =" + d);

			// You need to lower the third arm so that the block sits just above the
			// bar
			// For part (a) all bars are initially set to 7
			// For Parts (b) and (c) you must extract this value from the array
			
			// lowering third arm
			int target = 0;
			if(blockHeights[i] == 1)
			{
				target = targetCol1Ht;
			}
			else if(blockHeights[i] == 2)
			{
				target = targetCol2Ht;
			}
			else
			{
				target = barHeights[currentBar-3];
			}
			while ((h - 1) - d - blockHeights[i] > target)
			{
				r.lower();
				d++;
			}

			System.out.println("Debug 5: height(arm1)= " + h + " width (arm2) = "
					+ w + " depth (arm3) =" + d);

			// dropping the block
			r.drop();
			if(blockHeights[i] == 1)
			{
				targetCol1Ht += blockHeights[i];
			}
			else if(blockHeights[i] == 2)
			{
				targetCol2Ht += blockHeights[i];
			}
			else
			{
				barHeights[currentBar-3] += blockHeights[i];
				currentBar++;
			}
			// The height of currentBar increases by block just placed
			

			// raising the third arm all the way
			while (d > 0)
			{
				r.raise();
				d--;
			}
			System.out.println("Debug 6: height(arm1)= " + h + " width (arm2) = "
					+ w + " depth (arm3) =" + d);


			
			
			//contractAmt = 7-currentBar;

		}
		

	}

	public void run(int barHeights[], int blockHeights[])
	{
		
	}
}
