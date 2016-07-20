package org.usfirst.frc3620.FRC3620_Killer_Rabbit;

import java.util.Date;

public class VisionData {
		double height, width, x, y, rt;
		int count;
		long whenRecieved;

		public double getImageWidth() {
			return width;
		}

		public long getWhenRecieved() {
			return whenRecieved;
		}

		public double getX() {
			return x;
		}

		@Override
		public String toString() {
			return "VisionData [height=" + height + ", width=" + width + ", x="
					+ x + ", y=" + y + ", rt=" + rt + ", count=" + count
					+ ", whenRecieved=" + whenRecieved + "]";
		}
	}