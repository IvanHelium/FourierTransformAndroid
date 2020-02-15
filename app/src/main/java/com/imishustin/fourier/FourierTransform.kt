package com.imishustin.fourier

import com.jjoe64.graphview.series.DataPoint
import kotlin.math.*


data class FourierTransformDistribution( val N : Int){

}

class FourierTransform(
   val N : Int = 4, //number of fourier transform components (numbers of frequencies)
   val fs : Double = 1000.0 // sampling frequency
   ) {



   private val dataSignalArraySize = 8000
   private var dataSignalArray : Array<Double> = Array<Double>(dataSignalArraySize){0.0}

   var realArray : Array<Double>  = Array<Double>(N){0.0}
   var imageArray : Array<Double> = Array<Double>(N){0.0}
   var amplitudeArray : Array<Double>  = Array<Double>(N){0.0}
   var phaseArray : Array<Double>  = Array<Double>(N){0.0}

   public fun discreteFourierTransform(){

      for (i in 0 until N) {

         for(j in 0 until N) {

               realArray[i] = realArray[i] + dataSignalArray[j* dataSignalArray.size/N] * cos(2.0 * PI * j * i / N)
               imageArray[i] = imageArray[i] + dataSignalArray[j* dataSignalArray.size/N] * sin(2.0 * PI * j * i / N) * (-1.0)

         }
      }

      for(i in 0 until N) {
         amplitudeArray[i] = sqrt( Math.pow(realArray[i],2.0) +  Math.pow(imageArray[i],2.0)  )
         phaseArray[i] = atan(imageArray[i] / realArray[i])
      }
   }

   public fun setDataArray(arr : Array<Double>){
      this.dataSignalArray = arr
   }

   public fun setDataArrayFromDataPoint(arr : Array<DataPoint?>){

      if(dataSignalArray.size > arr.size)
      {
         return
      }
      for (i in 0 until dataSignalArray.size) {
         dataSignalArray[i] = arr[i]!!.y
      }

   }



}