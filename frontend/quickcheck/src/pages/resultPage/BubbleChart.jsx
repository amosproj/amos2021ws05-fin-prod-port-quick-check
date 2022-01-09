import React from 'react';
import { Chart, LineController,ArcElement, Tooltip, Legend , LineElement, PointElement, LinearScale, Title} from 'chart.js';
import { Bubble } from 'react-chartjs-2';
import annotationPlugin from 'chartjs-plugin-annotation';

Chart.register(LineController, LineElement, PointElement, LinearScale, Title, annotationPlugin);

const annotation2 = {
  type: 'box',
  backgroundColor: 'rgba(82,8,129)',
  borderColor: 'rgb(101, 33, 171)',
  borderWidth: 1,
  click: function({chart, element}) {
    console.log('Box annotation clicked');
  },
  drawTime: 'beforeDatasetsDraw',
  xMax: 5,
  xMin: 0,
  yMax: 40,
  yMin: 20,
};
const data = {
  datasets: [{
    label: 'Dummy Data 1',
    data: [{
      x: 150,
      y: -150,
      r: 20
    }],
    backgroundColor: 'rgba(255,72,166)'
},
{
  label: 'Dummy Data 2',
  data: [{
    x: -140,
    y: 100,
    r: 30}
],
  backgroundColor: 'rgba(147,213,34)'
},{
label: 'Dummy Data 3',
data: [{
  x: -60,
  y: 30,
  r: 60}
],
backgroundColor: 'rgba(41,213,255)'
},{
label: 'Dummy Data 4',
data: [{
  x: 10,
  y: 0,
  r: 70}
],
backgroundColor: 'rgba(82,8,129)'
}]
};
const options = {
    plugins: {
      annotation: {
        annotations: {
            green: {
                  drawTime: 'beforeDatasetsDraw',
                type: 'box',
                xMin: 0,
                xMax: -200,
                xScaleID: 'x',
                yMin: 0,
                yMax: -200,
                  yScaleID: 'y',
                backgroundColor: 'rgba(218, 247, 166, .5)'
            },
            red: {
                  drawTime: 'beforeDatasetsDraw',
                type: 'box',
                xMin: 0,
                xMax: 200,
                xScaleID: 'x',
                yMin: 0,
                yMax: 200,
                  yScaleID: 'y',
                backgroundColor: 'rgba(251, 133, 129, .5)'
            },

            line: {
                  type: 'line',
                  borderColor: 'black',
  borderWidth: 5,
  label: {
    backgroundColor: 'red',
    content: 'Test Label',
    enabled: true
  },
  scaleID: 'y',
                }
        }
    }
  }
};
function BubbleGraph(props) {
  return <Bubble data={data} options={options} />;
}

export default BubbleGraph;
