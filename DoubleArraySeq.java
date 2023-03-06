package solution;

/**
 * @author Andrew Blackwell
 * @version 2.23.23
 */

public class DoubleArraySeq implements Cloneable 
{
  public final static int DEFAULT_CAPACITY = 10;
  private int manyItems;
  private int currentIndex;
  private double[] data;

  public DoubleArraySeq() throws OutOfMemoryError 
  {
    try
    {
      data = new double[DEFAULT_CAPACITY];
      currentIndex = 0;
      manyItems = 0;
    }
    catch (OutOfMemoryError outmem)
    {
      outmem.printStackTrace();
    }
  }

  public DoubleArraySeq(int initialCapacity) throws OutOfMemoryError
  {
    try
    {
      if (initialCapacity <= 0)
      {
        throw new IllegalArgumentException("initialCapacity cannot be negative.");
      }
      else
      {
        data = new double[initialCapacity];
        manyItems = 0;
        currentIndex = 0;
      }
    }
    catch (OutOfMemoryError outmem)
    {
       outmem.printStackTrace();
    }
  }

  public void addAfter(double element) throws OutOfMemoryError 
  {
    try
    {
      if(getCapacity() == manyItems)
      {
        ensureCapacity(manyItems + 1);
      }
      if (isCurrent() == true)
      {
        for (int count = manyItems; count > (currentIndex + 1); count--)
        {
          data[count] = data[count - 1];
        }
        currentIndex++;
        data[currentIndex] = element;
        manyItems++;
      }
      else
      {
        currentIndex = manyItems;
        data[currentIndex] = element;
        manyItems++;
      }
    }
    catch (OutOfMemoryError outmem)
    {
      outmem.printStackTrace();
    }
  }

  public void addBefore(double element) throws OutOfMemoryError 
  {
    try
    {
      if (manyItems == getCapacity())
      {
        ensureCapacity(manyItems + 1);
      }
      if (isCurrent() == true)
      {
        for (int count = manyItems; count > 0; count--)
        {
          data[count] = data[count - 1];
        }
        manyItems++;
        data[currentIndex] = element;
      }
      else
      {
        for (int count = manyItems; count > 0; count--)
        {
          data[count] = data[count - 1];
        }
        currentIndex = 0;
        manyItems++;
        data[currentIndex] = element;
      }
    }
    catch (OutOfMemoryError outmem)
    {
      outmem.printStackTrace();
    }
  }
  
  public void addAll(DoubleArraySeq addend) throws NullPointerException, OutOfMemoryError 
  {
    try
    {
      if (addend == null)
      {
        throw new NullPointerException("no such addend");
      }
      else
      {
        ensureCapacity(manyItems + addend.manyItems);
        System.arraycopy(addend.data, 0, data, manyItems, addend.manyItems);
        manyItems = manyItems + addend.manyItems;
      }
    }
    catch (OutOfMemoryError outmem)
    {
      outmem.printStackTrace();
    }
  }

  public void advance() throws IllegalStateException 
  {
    if (isCurrent())
    {
      currentIndex++;
    }
    else
    {
      throw new IllegalStateException("no current element to advance.");
    }
  }

  @Override
  public DoubleArraySeq clone() throws OutOfMemoryError, RuntimeException 
  {
    DoubleArraySeq dummy = new DoubleArraySeq();
    try
    {
      try
      {
       dummy = (DoubleArraySeq) super.clone();
      }
      catch(CloneNotSupportedException c)
      {
        throw new RuntimeException("cloneable not supported.");
      }
      dummy.data = data.clone();
    }
    catch (OutOfMemoryError outmem)
    {
      outmem.printStackTrace();
    }
    return dummy;
  }

  public static DoubleArraySeq concatenation(DoubleArraySeq s1, DoubleArraySeq s2) throws NullPointerException, OutOfMemoryError 
  {
    DoubleArraySeq concat = new DoubleArraySeq();
    concat.ensureCapacity(s1.manyItems + s2.manyItems);
    System.arraycopy(s1, 0, concat.data, 0, s1.manyItems);
    System.arraycopy(s2, 0, concat.data, s1.manyItems, s2.manyItems);
		concat.manyItems = (s1.manyItems + s2.manyItems);
    concat.currentIndex = concat.manyItems;
    return concat;
  }

  public void ensureCapacity(int minimumCapacity)
      throws OutOfMemoryError 
  {
    try
      {
        double bigArr[];
        
        if(getCapacity() < minimumCapacity)
        {
          bigArr = new double[minimumCapacity];
          System.arraycopy(data, 0, bigArr, 0, manyItems);
          data = bigArr;
        }
      }
    catch (OutOfMemoryError outmem)
      {
        outmem.printStackTrace();
      }
  }

  public int getCapacity() 
  {
    return data.length;
  }

  public double getCurrent() throws IllegalStateException 
  {
    if (isCurrent())
    {
      return data[currentIndex];
    }
    else
    {
      throw new IllegalStateException("no current element.");
    }
  }

  public boolean isCurrent() 
  {
    return (currentIndex < manyItems);
  }

  public void removeCurrent()
      throws IllegalStateException 
  {
    if (isCurrent())
    {
      for (int count = currentIndex; count < manyItems; count++)
      {
        data[count] = data[count + 1];
      }
      manyItems--;
    }
    else
    {
      throw new IllegalStateException("no current element.");
    }
  }

  public int size() 
  {
    return manyItems;
  }

  public void start() 
  {
    if (manyItems > 0)
    {
      currentIndex = 0;
    }
    else
    {
      throw new IllegalStateException("cannot start empty sequence.");
    }
  }

  public void trimToSize()
      throws OutOfMemoryError 
  {
    try
      {
        if (getCapacity() > manyItems)
        {
          double[] freshCut = new double[manyItems];
          System.arraycopy(data, 0, freshCut, 0, manyItems);
          data = freshCut;
        }
      }
    catch (OutOfMemoryError outmem)
      {
        outmem.printStackTrace();
      }
  }

  @Override
  public String toString() 
  {
    String str = "<";
    for (int count = 0; count < manyItems; count++)
    {
      if (count == currentIndex)
      {
        str += "[";
      }
      str += data[count];
      if (count == currentIndex)
      {
        str += "]";
      }
      if (count != manyItems - 1)
      {
        str += ", ";
      }
    }
    str += ">";
    return str;
  }
  
  @Override
  public boolean equals(Object other) 
  {
    boolean e = false;
    if(other instanceof DoubleArraySeq)
    {
      e = ((DoubleArraySeq) other).toString().equals(this.toString());
    }
    return e;
  }
}
