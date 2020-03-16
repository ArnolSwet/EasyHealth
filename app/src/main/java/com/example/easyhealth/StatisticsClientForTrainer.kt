package com.example.easyhealth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries

class StatisticsClientForTrainer : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistics_client_for_trainer)
        val monthGraph = findViewById(R.id.monthGraph) as GraphView

        val series1 = LineGraphSeries(arrayOf(DataPoint(0,1),DataPoint(1,5),DataPoint(2,3)))
        monthGraph.addSeries(series1)
    }
}
