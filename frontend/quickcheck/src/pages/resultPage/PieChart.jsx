import React from 'react'
import { Chart as ChartJS, ArcElement, Tooltip, Legend } from 'chart.js';
import { Pie } from 'react-chartjs-2';


ChartJS.register(ArcElement, Tooltip, Legend);


const options = {
    plugins: {
        title: {
            display: true,
            text: 'Pie Chart',
            color:'blue',
            font: {
                size:20
            },
            padding:{
                top:3,
                bottom:3
            },
            animation:{
                animateScale: true
            }
        }
    }

}
function PieChartGraph(props) {
    return (
           <Pie data={props.data} options={options} />
    )
}

export default PieChartGraph
