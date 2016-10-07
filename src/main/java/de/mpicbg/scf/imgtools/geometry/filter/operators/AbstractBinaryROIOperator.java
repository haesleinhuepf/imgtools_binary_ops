package de.mpicbg.scf.imgtools.geometry.filter.operators;

import net.imglib2.*;
import net.imglib2.roi.util.Contains;
import net.imglib2.roi.util.ContainsRandomAccess;
import net.imglib2.type.BooleanType;
import net.imglib2.type.logic.BoolType;

/**
 * This is an abstract class as base for all binary operators which allow combining discree regions of interest
 * like in the theory of sets.
 * Example: intersection of two ROIs.
 *
 *
 * Author: Robert Haase, Scientific Computing Facility, MPI-CBG Dresden, rhaase@mpi-cbg.de
 * Date: September 2016
 */
public abstract class AbstractBinaryROIOperator<B1 extends BooleanType<B1>, B2 extends BooleanType<B2>> extends AbstractInterval implements RandomAccessibleInterval<BoolType>, Contains<RealLocalizable>{

    protected RandomAccessibleInterval<B1> leftOperand;
    protected RandomAccessibleInterval<B2> rightOperand;

    public AbstractBinaryROIOperator(RandomAccessibleInterval<B1> leftOperand, RandomAccessibleInterval<B2> rightOperand)
    {
        super(leftOperand.numDimensions());
        for ( int d = 0; d < n; ++d )
            this.max[ d ] = leftOperand.max(d);
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;

    }



    @Override
    public RandomAccess<BoolType> randomAccess() {
        return new ContainsRandomAccess(this);
    }

    @Override
    public RandomAccess<BoolType> randomAccess(Interval interval) {

        return randomAccess();
    }

    @Override
    public abstract boolean contains(RealLocalizable l);

    @Override
    public Contains<RealLocalizable> copyContains() {
        return this;
    }


    @Override
    public long min( final int d )
    {
        return Math.min(leftOperand.min(d), rightOperand.min(d));
    }

    @Override
    public void min( final long[] min )
    {
        for ( int d = 0; d < n; ++d )
            min[ d ] = min(d);
    }

    @Override
    public void min( final Positionable min )
    {
        for ( int d = 0; d < n; ++d )
            min.setPosition( min(d), d );
    }

    @Override
    public long max( final int d )
    {

        return Math.max(leftOperand.max(d), rightOperand.max(d));
    }

    @Override
    public void max( final long[] max )
    {
        for ( int d = 0; d < n; ++d )
            max[ d ] = max(d) ;
    }

    @Override
    public void max( final Positionable max )
    {
        for ( int d = 0; d < n; ++d )
            max.setPosition( max(d), d );
    }
}
