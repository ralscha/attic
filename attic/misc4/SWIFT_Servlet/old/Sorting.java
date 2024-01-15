
public final class Sorting {

    public static void quickSort(Object[] data, int lo, int hi, BinaryPredicate predicate ) {
        if(lo >= hi) return;

        int mid = ( lo + hi ) / 2;

        if (predicate.execute(data[lo], data[mid])) {
          Object tmp = data[lo];
          data[lo]   = data[mid];
          data[mid]  = tmp;
        }

        if( predicate.execute( data[ mid ], data[ hi ] ) ) {
          Object tmp = data[ mid ];
          data[ mid ] = data[ hi ];
          data[ hi ] = tmp;

          if( predicate.execute( data[ lo ], data[ mid ] ) ) {
            Object tmp2 = data[ lo ];
            data[ lo ] = data[ mid ];
            data[ mid ] = tmp2;
            }
          }

         int left = lo + 1;
         int right = hi - 1;

         if( left >= right ) 
           return; 

         Object partition = data[ mid ];

         for( ;; ) 
          {
          while( predicate.execute( data[ right ], partition ) )
            {
            --right;
            }

          while( left < right && !predicate.execute( data[ left ], partition ) )
            {
            ++left;
            }

          if( left < right ) {
            Object tmp = data[ left ];
            data[ left ] = data[ right ];
            data[ right ] = tmp;
            --right;
          } else {
            break;
          }
        }

        quickSort( data, lo, left, predicate );
        quickSort( data, left + 1, hi, predicate );
    }
}
