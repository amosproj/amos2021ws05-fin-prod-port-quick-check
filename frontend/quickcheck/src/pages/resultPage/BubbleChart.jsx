import React from 'react';
import { useState, useEffect } from 'react';
import { Chart, LineController, LineElement, PointElement, LinearScale, Title, getContext } from 'chart.js';
import { Bubble } from 'react-chartjs-2';
import annotationPlugin from 'chartjs-plugin-annotation';

Chart.register(LineController, LineElement, PointElement, LinearScale, Title, annotationPlugin);

function BubbleGraph({ data }) {
  const options = {

    scales: {
      y: {
        title: {
          display: true,
          text: 'Margin',
          align: 'end',
        },
        ticks: {
          callback: function (value, index, values) {
            return '';
          },
        },
      },
      x: {
        title: {
          display: true,
          text: 'Cost/Complexity',
          align: 'end',
        },
        ticks: {
          callback: function (value, index, values) {
            return '';
          },
        },
      },
    },
    plugins: {
        responsive: true,
        tooltip: {
            callbacks: {
        label: function(tooltipItem, data) {
        //console.log(tooltipItem)
          var dataset = tooltipItem["dataset"];
          //console.log(dataset)
          var index = tooltipItem.index;
          return [dataset["label"]+":" , "Margin:"+ dataset["data"][0]["y"]+ ", Cost/Complexity: " + dataset["data"][0]["x"] + ", Volume in Euro: "+ dataset["data"][0]["r"]] ;
        }
    }
},

      title: {
        display: true,
        text: 'Consolidated Overview',
        color: 'black',
        borderWidth: 2,
        font: {
          size: 20,
        },
      },
      annotation: {
        annotations: {
          green: {
            drawTime: 'beforeDatasetsDraw',
            type: 'box',
            xMin: 0,
            xMax: -200,
            xScaleID: 'x',
            yMin: 0,
            yMax: 200,
            yScaleID: 'y',
            backgroundColor: 'rgba(218, 247, 166, .5)',
            click: function ({ chart, element }) {
              console.log('Green Box annotation clicked');
            },
          },
          red: {
            drawTime: 'beforeDatasetsDraw',
            type: 'box',
            xMin: 0,
            xMax: 200,
            xScaleID: 'x',
            yMin: 0,
            yMax: -200,
            yScaleID: 'y',
            backgroundColor: 'rgba(251, 133, 129, .5)',
            click: function ({ chart, element }) {

              console.log('Red Box annotation clicked');
            },
          },
          scale: {
            type: 'label',
            xValue: -10,
            yValue: 180,
            content: ['Scale', ''],
            font: {
              size: 10,
            },
          },
          review1: {
            type: 'label',
            xValue: 189,
            yValue: 180,
            content: ['Review', ''],
            font: {
              size: 10,
            },
          },
          review2: {
            type: 'label',
            xValue: -11,
            yValue: -195,
            content: ['Review', ''],
            font: {
              size: 10,
            },
          },
          ds: {
            type: 'label',
            xValue: 175,
            yValue: -180,
            color: 'rgba(239, 65, 56, .7 )',
            content: ['Discontinuation', 'Strategy'],
            font: {
              size: 10,
              style: 'inherit',
            },
          },
        },
      },
    },
  };
  var myChart = <Bubble data={data} options={options} />;

  return myChart;
}

export default BubbleGraph;
