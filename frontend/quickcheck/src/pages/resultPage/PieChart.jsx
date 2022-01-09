import React from 'react';
import { Chart, ArcElement, Tooltip, Legend } from 'chart.js';
import { Pie } from 'react-chartjs-2';

Chart.register(ArcElement, Tooltip, Legend);
var ctx = document.getElementById("myChart");
var ChartData = {
  labels: [],
  datasets: [
      {data: [25, 2, 25, 5, 23],
      backgroundColor: [
        'rgba(147,213,34)',
        'rgba(41,213,255)',
        'rgba(82,8,129)',
        'rgba(255,72,166)',
        'rgba(108,3,168)',
    ]},
      {
    data: [300, 50, 100],
    backgroundColor: [
      "#FF6384",
      "#36A2EB",
      "#FFCE56"
    ],
    hoverBackgroundColor: [
      "rgba(108,3,168)",
      "#36A2EB",
      "#FFCE56"
    ]
  }, {
    data: [200, 100, 25, 25, 66, 34],
    backgroundColor: [
      "#FF6384",
      "#36A2EB",
      "#FFCE56",
      "#FF6384",
      "#36A2EB",
      "#FFCE56"
    ],
    hoverBackgroundColor: [
      "#FF6384",
      "#36A2EB",
      "#FFCE56",
      "#FF6384",
      "#36A2EB",
      "#FFCE56"
    ]
  }]
};

const options = {
  plugins: {
    title: {
      display: true,
      text: 'Pie Chart',
      color: 'white',
      borderWidth: 3,
      font: {
        size: 20,
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
function PieChartGraph(props) {
  return <Pie data={ChartData} options={options} />;
}

export default PieChartGraph;
