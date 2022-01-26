import React from 'react';
import { Chart, ArcElement, Tooltip, Legend } from 'chart.js';
import { Pie } from 'react-chartjs-2';

Chart.register(ArcElement, Tooltip, Legend);
var yellow = 'rgba(255, 195, 0, .7)';
var green = 'rgba(131, 239, 56, .7 )';
var red = 'rgba(239, 65, 56, .7 )';

var fullyellow = 'rgba(255, 195, 0, 1)';
var fullgreen = 'rgba(131, 239, 56,1 )';
var fullred = 'rgba(239, 65, 56, 1)';

function PieChartGraph({ data_outer, data_inner, title, color, ratings }) {
  var ChartData = {
    labels: [],
    datasets: [
      {

          label: "Client",
        data: [0],
        backgroundColor: [green, yellow, red],
        hoverBackgroundColor: [fullgreen, fullyellow, fullred],

      },
      {

         label: "Product Variant",
        data: [0],
        backgroundColor: [green, yellow, red],
        hoverBackgroundColor: [fullgreen, fullyellow, fullred],
      },
      {
        rating:[],
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
              var dataset = tooltipItem["dataset"];
              //var index = tooltipItem.index;
              var rName="";
              var to_return= " "+ dataset["label"]
              if ((dataset["label"] === "Client" ) || (dataset["label"] === "Product Variant" )){
                  if  (tooltipItem["dataIndex"]===0){
                      to_return= [to_return + ":" ,  + tooltipItem["formattedValue"] + "% Simple"] ;
                  }
                  if  (tooltipItem["dataIndex"]===1){
                      to_return= [to_return + ":" ,  + tooltipItem["formattedValue"] + "% Medium"] ;
                  }
                  if  (tooltipItem["dataIndex"]===2){
                      to_return= [to_return + ":" ,  + tooltipItem["formattedValue"] + "% Complex"] ;
                  }
              }
              else{
                  //console.log(dataset["rating"][0])
                  if (typeof (dataset["rating"][0]["score"] )== 'undefined') {
                       to_return= [(to_return + " Ratings:") ,   "None"];
                  }
                  else{
                      to_return= [to_return + " Ratings:"]

                      for (let x = 0; x < dataset["rating"].length; x++) {
                          if (dataset["rating"][x]["score"] ==='GERING'){
                              rName="Simple";
                          }
                          else if (dataset["rating"][x]["score"] === 'MITTEL'){
                              rName="Medium";
                          }
                          else if (dataset["rating"][x]["score"] === 'HOCH'){
                              rName="Complex";
                          }
                          else{
                               rName= dataset["rating"][x]["score"];
                          }
                          to_return.push((rName+ " : " + dataset["rating"][x]["count"]));

                      }
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
  ChartData['datasets'][2]['rating'] =ratings;


  return <Pie data={ChartData} options={options} />;
}

export default PieChartGraph;
