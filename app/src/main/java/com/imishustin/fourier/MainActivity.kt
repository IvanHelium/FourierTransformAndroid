package com.imishustin.fourier

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import kotlin.math.sin


class MainActivity : AppCompatActivity() {


    var series = LineGraphSeries<DataPoint>() //like global variable =)
    var series_2 = LineGraphSeries<DataPoint>() //like global variable =)

    var fourierTransform = FourierTransform(8, 1000.0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val arrayRaw = arrayOfNulls<DataPoint?>(8000)
        val arrayTransormed = arrayOfNulls<DataPoint?>(8000)
        var dtDivader = 6.35  //6.35
        var dt : Double

        val testButton = findViewById(R.id.test_button) as Button
        var graph_1  = findViewById<GraphView>(R.id.graph_1) as GraphView
        var graph_2  = findViewById<GraphView>(R.id.graph_2) as GraphView

        dt = 0.0
        for(i in 0 until arrayTransormed.size){
            //array[i] = DataPoint(dt, (sin(dt / 4) + 2.0 * sin(dt / 8.0) ))
            arrayTransormed[i] = DataPoint(dt, 0.0)
            dt = dt + 0.01
        }
        dt = 0.0
        for(i in 0 until arrayRaw.size){
            //array[i] = DataPoint(dt, (sin(dt / 4) + 2.0 * sin(dt / 8.0) ))
            arrayRaw[i] = DataPoint(dt, ( sin(dt / dtDivader) + sin(dt / dtDivader / 2.0)))
            dt = dt + 0.01
        }
        series = LineGraphSeries<DataPoint>(arrayRaw)
        graph_1.addSeries(series)

        series_2 = LineGraphSeries<DataPoint>(arrayTransormed)
        graph_2.addSeries(series_2)


        testButton.setOnClickListener {
            // your code to perform when the user clicks on the button
            dtDivader -= 0.1
            testButton.setText(dtDivader.toString())
            dt = 0.0
            for(i in 0 until arrayRaw.size){
                //array[i] = DataPoint(dt, (sin(dt / 4) + 2.0 * sin(dt / 8.0) ))
                arrayRaw[i] = DataPoint(dt, ( sin(dt / dtDivader) ))
                dt = dt + 0.01
            }

            graph_1.removeAllSeries()
            series = LineGraphSeries<DataPoint>(arrayRaw)
            graph_1.addSeries(series)

            fourierTransform.setDataArrayFromDataPoint(arrayRaw)
            fourierTransform.discreteFourierTransform()

            dt = 0.0
            for(i in 0 until 8){
                arrayTransormed[i * 1000] = DataPoint(dt, fourierTransform.amplitudeArray[i] )
                dt = dt + 0.01 * 1000.0
            }

            graph_2.removeAllSeries()
            series_2 = LineGraphSeries<DataPoint>(arrayTransormed)
            graph_2.addSeries(series_2)


        }
    }
}
