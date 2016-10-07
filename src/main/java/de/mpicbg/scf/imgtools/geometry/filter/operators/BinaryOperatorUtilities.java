package de.mpicbg.scf.imgtools.geometry.filter.operators;

import net.imglib2.RandomAccessibleInterval;
import net.imglib2.type.BooleanType;
import net.imglib2.type.logic.BoolType;

/**
 * Author: Robert Haase, Scientific Computing Facility, MPI-CBG Dresden, rhaase@mpi-cbg.de
 * Date: September 2016
 */
public class BinaryOperatorUtilities {

    public static <B1 extends BooleanType<B1>, B2 extends BooleanType<B2>> RandomAccessibleInterval<BoolType>
    intersection(RandomAccessibleInterval<B1> leftOperand, RandomAccessibleInterval<B2> rightOperand)
    {
        return new BinaryIntersection<B1, B2>(leftOperand, rightOperand);
    }


    public static <B1 extends BooleanType<B1>, B2 extends BooleanType<B2>> RandomAccessibleInterval<BoolType>
    and(RandomAccessibleInterval<B1> leftOperand, RandomAccessibleInterval<B2> rightOperand)
    {
        return intersection(leftOperand, rightOperand);
    }


    public static <B1 extends BooleanType<B1>, B2 extends BooleanType<B2>> RandomAccessibleInterval<BoolType>
    union(RandomAccessibleInterval<B1> leftOperand, RandomAccessibleInterval<B2> rightOperand)
    {
        return new BinaryUnion<B1, B2>(leftOperand, rightOperand);
    }


    public static <B1 extends BooleanType<B1>, B2 extends BooleanType<B2>> RandomAccessibleInterval<BoolType>
    or(RandomAccessibleInterval<B1> leftOperand, RandomAccessibleInterval<B2> rightOperand)
    {
        return union(leftOperand, rightOperand);
    }



    public static <B extends BooleanType<B>> RandomAccessibleInterval<BoolType>
    negation(RandomAccessibleInterval<B> leftOperand)
    {
        return new BinaryNegation<B>(leftOperand);
    }

    public static <B extends BooleanType<B>> RandomAccessibleInterval<BoolType>
    wrap(RandomAccessibleInterval<B> leftOperand)
    {
        return new BinaryWrap<B>(leftOperand);
    }

    public static <B1 extends BooleanType<B1>, B2 extends BooleanType<B2>> RandomAccessibleInterval<BoolType>
    subtraction(RandomAccessibleInterval<B1> leftOperand, RandomAccessibleInterval<B2> rightOperand)
    {
        return intersection(leftOperand, new BinaryNegation<B2>(rightOperand));
    }
}
