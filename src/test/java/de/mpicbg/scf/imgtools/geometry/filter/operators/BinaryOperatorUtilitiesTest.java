package de.mpicbg.scf.imgtools.geometry.filter.operators;

import net.imglib2.Cursor;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.img.Img;
import net.imglib2.img.array.ArrayImgs;
import net.imglib2.roi.Regions;
import net.imglib2.type.BooleanType;
import net.imglib2.type.logic.BitType;
import net.imglib2.type.logic.BoolType;
import net.imglib2.view.Views;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Author: Robert Haase, Scientific Computing Facility, MPI-CBG Dresden, rhaase@mpi-cbg.de
 * Date: September 2016
 */
public class BinaryOperatorUtilitiesTest {
    @Test
    public void testNegation()
    {
        RandomAccessibleInterval<BitType> operand1 = getTestBinaryImage("0001110000");

        RandomAccessibleInterval<BoolType> negation = BinaryOperatorUtilities.negation(operand1);
        String test = getStringFromBinaryImage(negation);
        assertTrue("negation works !o1", test.equals("1110001111"));
    }


    @Test
    public void testSubtraction()
    {
        RandomAccessibleInterval<BitType> operand1 = getTestBinaryImage("0001111100");
        RandomAccessibleInterval<BitType> operand2 = getTestBinaryImage("0000011100");

        RandomAccessibleInterval<BoolType> subtraction = BinaryOperatorUtilities.subtraction(operand1, operand2);
        String test = getStringFromBinaryImage(subtraction);
        assertTrue("subtraction works o1\\o2", test.equals("0001100000"));
    }

    @Test
    public void testIntersection()
    {
        RandomAccessibleInterval<BitType> operand1 = getTestBinaryImage("0001110000");
        RandomAccessibleInterval<BitType> operand2 = getTestBinaryImage("0000111000");
        RandomAccessibleInterval<BitType> operand3 = getTestBinaryImage("0000011100");

        RandomAccessibleInterval<BoolType> intersection = BinaryOperatorUtilities.intersection(operand1, operand2);
        String test = getStringFromBinaryImage(intersection);
        assertTrue("intersection works o1&&o2", test.equals("0000110000"));

        intersection = BinaryOperatorUtilities.intersection(intersection, operand3);
        test = getStringFromBinaryImage(intersection);
        assertTrue("intersection works (o1&&o2)&&o3", test.equals("0000010000"));
    }

    @Test
    public void testUnion()
    {
        RandomAccessibleInterval<BitType> operand1 = getTestBinaryImage("0001110000");
        RandomAccessibleInterval<BitType> operand2 = getTestBinaryImage("0000111000");
        RandomAccessibleInterval<BitType> operand3 = getTestBinaryImage("0000011100");

        RandomAccessibleInterval<BoolType> union = BinaryOperatorUtilities.union(operand1, operand2);
        String test = getStringFromBinaryImage(union);
        assertTrue("union works o1||o2", test.equals("0001111000"));

        union = BinaryOperatorUtilities.union(union, operand3);
        test = getStringFromBinaryImage(union);
        assertTrue("union works (o1||o2)||o3", test.equals("0001111100"));
    }

    @Test
    public void testIfCursorOnIntersectionWorks()
    {
        RandomAccessibleInterval<BitType> operand1 = getTestBinaryImage("0001110000");
        RandomAccessibleInterval<BitType> operand2 = getTestBinaryImage("0000111000");
        Cursor<Void> cursor1 = Regions.iterable(operand1).cursor();
        cursor1.next();
        assertTrue (cursor1.getDoublePosition(0) == 3);
        cursor1.next();
        assertTrue (cursor1.getDoublePosition(0) == 4);
        cursor1.next();
        assertTrue (cursor1.getDoublePosition(0) == 5);
        assertTrue (!cursor1.hasNext());

        RandomAccessibleInterval<BoolType> intersection = BinaryOperatorUtilities.intersection(operand1, operand2);
        Cursor<Void> cursor = Regions.iterable(intersection).cursor();
        cursor.next();
        assertTrue (cursor.getDoublePosition(0) == 4);
        cursor.next();
        assertTrue (cursor.getDoublePosition(0) == 5);
        assertTrue (!cursor.hasNext());
    }

    RandomAccessibleInterval<BitType> getTestBinaryImage(String content)
    {
        Img<BitType> img = ArrayImgs.bits(new long[]{content.length()});
        int count = 0;
        for (BitType b : img) {
            b.set(content.substring(count, count+1).equals("1"));
            count++;
        }
        return img;
    }



    <B extends BooleanType<B>> String getStringFromBinaryImage(RandomAccessibleInterval<B> rai)
    {
        String res = "";
        for (B b : Views.iterable(rai)) {
            if (b.get())
            {
                res = res + "1";
            }
            else
            {
                res = res + "0";
            }
        }
        return res;
    }

}