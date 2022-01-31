import React from 'react';
import { Chart, LineController, LineElement, PointElement, LinearScale, Title } from 'chart.js';
import { Bubble } from 'react-chartjs-2';
import annotationPlugin from 'chartjs-plugin-annotation';

Chart.register(LineController, LineElement, PointElement, LinearScale, Title, annotationPlugin);

function BubbleGraph({ data, minbY, maxbY, minbX, maxbX }) {
  //console.log("limits", minbY, maxbY, minbX, maxbX)

  var options = {
    scales: {
      y: {
        max: maxbY,
        min: minbY,
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
        max: maxbX,
        min: minbX,
        title: {
          display: true,
          text: 'Economic Value/Complexity',
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
          label: function (tooltipItem, data) {
            //console.log(tooltipItem)
            var dataset = tooltipItem['dataset'];
            //console.log(dataset)
            //var index = tooltipItem.index;
            return [
              dataset['label'] + ':',
              'Margin:' +
                dataset['data'][0]['y'] +
                ', Economic Value/Complexity: ' +
                dataset['data'][0]['x'] +
                ', Volume in Euro (Millions): ' +
                dataset['data'][0]['volume'],
            ];
          },
        },
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
            xMin: minbX,
            xMax: (maxbX - minbX) / 2,
            xScaleID: 'x',
            yMin: (maxbY - minbY) / 2,
            yMax: maxbY,
            yScaleID: 'y',
            backgroundColor: 'rgba(218, 247, 166, .5)',
            click: function ({ chart, element }) {
              console.log('Green Box annotation clicked');
            },
          },
          red: {
            drawTime: 'beforeDatasetsDraw',
            type: 'box',
            xMin: (maxbX - minbX) / 2,
            xMax: maxbX,
            xScaleID: 'x',
            yMin: minbY,
            yMax: (maxbY - minbY) / 2,
            yScaleID: 'y',
            backgroundColor: 'rgba(251, 133, 129, .2)',
            click: function ({ chart, element }) {
              console.log('Red Box annotation clicked');
            },
          },
          scale: {
            drawTime: 'afterDatasetsDraw',
            type: 'label',
            color: 'rgba(60, 179, 113, .7)',
            xValue: 0.9 * ((maxbX - minbX) / 2),
            yValue: 0.9 * maxbY,
            content: ['Scale', ''],
            font: {
              size: 10,
              style: 'inherit',
            },
          },
          review1: {
            type: 'label',
            color: 'rgba(255, 165, 0 )',
            xValue: 0.9 * maxbX,
            yValue: 0.9 * maxbY,
            content: ['Review', ''],
            font: {
              size: 10,
            },
          },
          review2: {
            type: 'label',
            color: 'rgba(255, 165, 0 )',
            xValue: 0.9 * ((maxbX - minbX) / 2),
            yValue: 1.1 * minbY,
            content: ['Review', ''],
            font: {
              size: 10,
            },
          },
          ds: {
            type: 'label',
            xValue: 0.9 * maxbX,
            yValue: minbY + 0.7,
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
