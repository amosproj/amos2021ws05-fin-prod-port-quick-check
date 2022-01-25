import React from 'react';
import { useState, useEffect } from 'react';
import { Chart, ArcElement, Tooltip, Legend } from 'chart.js';
import { Pie } from 'react-chartjs-2';

Chart.register(ArcElement, Tooltip, Legend);
var yellow = 'rgba(255, 195, 0, .7)';
var green = 'rgba(131, 239, 56, .7 )';
var red = 'rgba(239, 65, 56, .7 )';

var fullyellow = 'rgba(255, 195, 0, 1)';
var fullgreen = 'rgba(131, 239, 56,1 )';
var fullred = 'rgba(239, 65, 56, 1)';

function PieChartGraph({ data_outer, data_inner, title, color }) {
  var ChartData = {
    labels: [],
    datasets: [
      {
          label: "Client",
        data: [300, 50, 100],
        backgroundColor: [green, yellow, red],
        hoverBackgroundColor: [fullgreen, fullyellow, fullred],
      },
      {
          label: "Product Variant",
        data: [30, 5, 100],
        backgroundColor: [green, yellow, red],
        hoverBackgroundColor: [fullgreen, fullyellow, fullred],
      },
      {
        label: title,
        data: [1],
        borderWidth: [0],
        backgroundColor: [yellow],
        hoverBackgroundColor: [yellow],
      },
    ],
  };
  var options = {
    plugins: {
            responsive: true,
            tooltip: {
                callbacks: {
            label: function(tooltipItem, data) {
            console.log(tooltipItem, data)
              var dataset = tooltipItem["dataset"];
              console.log(dataset);
              var index = tooltipItem.index;
              var to_return= " "+ dataset["label"]
              if ((dataset["label"] == "Client" ) || (dataset["label"] == "Product Variant" )){
                  if  (tooltipItem["dataIndex"]==0){
                      to_return= [to_return + ":" ,  + tooltipItem["formattedValue"] + "% Simple"] ;
                  }
                  if  (tooltipItem["dataIndex"]==1){
                      to_return= [to_return + ":" ,  + tooltipItem["formattedValue"] + "% Medium"] ;
                  }
                  if  (tooltipItem["dataIndex"]==2){
                      to_return= [to_return + ":" ,  + tooltipItem["formattedValue"] + "% Complex"] ;
                  }
              }
              return( to_return);
            }
        }
    },
      title: {
        display: true,
        text: 'Pie Chart',
        color: 'black',
        borderWidth: 2,
        font: {
          size: 15,
        },
        padding: {
          top: 3,
          bottom: 3,
        },
        animation: {
          animateScale: true,
        },
      },
    },
  };

  options['plugins']['title']['text'] = title;
  ChartData['datasets'][0]['data'] = data_outer;
  ChartData['datasets'][1]['data'] = data_inner;
  ChartData['datasets'][2]['backgroundColor'] = color;
  ChartData['datasets'][2]['hoverBackgroundColor'] = color;
  useEffect(() => {

  }, []);
  return <Pie data={ChartData} options={options} />;
}

export default PieChartGraph;
