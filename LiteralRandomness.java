public class LiteralRandomness {

	public static void main(String[] args) {
		int iter = 1000000;
		int[] nums = new int[1000];
		double increment = 1.0/nums.length;
		for (int i = 0; i < iter; i++)
		{
			double upper = Math.PI;
			double random = Math.random() * upper;
			double result = Math.sin(random);
			nums[(int)(result / increment)]++;
		}
		double start = 0;
		for (int i = 0; i < nums.length; i++)
		{
			System.out.println(start + " to " + (start + increment) + ": " + nums[i]);
			start += increment;
		}
	}

}
