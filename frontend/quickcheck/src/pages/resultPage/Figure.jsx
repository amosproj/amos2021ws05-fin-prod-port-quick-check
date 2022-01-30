import React from 'react';
import { Heading, Flex, Icon, Text } from '@chakra-ui/react';

import Card from '../../components/Card';
import PieChartGraph from './PieChart';
import BubbleGraph from './BubbleChart';

var colors = [
  'rgba(255,72,166)',
  'rgba(147,213,34)',
  'rgba(41,213,255)',
  'rgba(82,8,129)',
  'rgba(1,24,56)',
];
var yellow = 'rgba(255, 195, 0, .7)';
var green = 'rgba(131, 239, 56, .7 )';
var red = 'rgba(239, 65, 56, .7 )';

const CircleIcon = (props) => (
  <Icon viewBox="10 1 200 200" {...props}>
    <path fill={props.color} d="m 100, 100 m -75, 0 a 75,75 0 1,0 150,0 a 75,75 0 1,0 -150,0" />
  </Icon>
);

function ShowPieCharts({ results }) {
  //console.log('test', { results });
  var rows = [];
  var kundenValues=[0,0,0]
  var index=0;
  for (let i = 0; i < results.length / 4; i++) {
    rows[i] = [];
    rows[i]['key'] = i;
    kundenValues=[0,0,0]
    for (let j = 0; j < 4 && i * 4 + j < results.length; j++) {
         index = i * 4 + j;
        for (let r=0; r< results[j]["ratings"].length; r++){
            if (results[j]["ratings"][r]["rating"]["criterion"]=='Kunde'){
                var kundenValuesTmp=results[j]["ratings"][r]["answer"].split(', ');
                for (let k=0; k<kundenValuesTmp.length; k++ ){
                    kundenValues[k]=parseFloat(kundenValuesTmp[k]);
                }
                //console.log(kundenValues);
                //results[i]["ratings"][r]["answer"]=kundenValues;
            }
        }


      var kundenTotal = 0;
      for (let x = 0; x < kundenValues.length; x++) {
        kundenTotal = kundenTotal + parseFloat(kundenValues[x]);
      }

      for (let x = 0; x < kundenValues.length; x++) {
        kundenValues[x] = (parseFloat(kundenValues[x]) / kundenTotal) * 100;
      }
      var ratingValues = results[index]['scores']; //

      //var ratingValues= [{'count':"1"},{'count': "2"}, {'count': "3"}]; //mock
      var ratingTotal = 0;
      rows[i][j] = [];

      for (let x = 0; x < ratingValues.length; x++) {
        //console.log("rating", ratingValues[x]['count'])
        ratingTotal = ratingTotal + ratingValues[x]['count'];
      }
      if (ratingTotal === 0) {
        ratingValues = [0];
      }
      for (let x = 0; x < ratingValues.length; x++) {
        ratingValues[x] = (parseFloat(ratingValues[x]['count']) / ratingTotal) * 100;
      }
      console.log(kundenValues);
      rows[i][j]['key'] = { index };
      rows[i][j][0] = colors[index % colors.length];
      rows[i][j][1] = results[index]['productName'];
      rows[i][j][2] = kundenValues;
      rows[i][j][3] = ratingValues;
      rows[i][j][4] = results[index]['scores'];
    }

  }
console.log(rows)
  return (
    <Flex
      flexDirection="column"
      w="full"
      gridGap={1}
      justifyContent="space-between"
      alignItems="stretch"
    >
      {rows.map((row) => (
        <PieChartRow key={row[0][0]} row_data={row}></PieChartRow>
      ))}
    </Flex>
  );
}

function PieChartRow({ row_data }) {
  return (
    <Flex
      flexDirection="row"
      w="full"
      gridGap={1}
      justifyContent="space-between"
      alignItems="stretch"
    >
      {row_data.map((pie) => (
        <Flex w="25%" key={pie[1]}>
          <PieChartGraph
            data_outer={pie[2]}
            data_inner={pie[3]}
            color={pie[0]}
            title={pie[1]}
            ratings={pie[4]}
          ></PieChartGraph>
        </Flex>
      ))}
    </Flex>
  );
}

function Figure({ results }) {
  var data = {
    click: function ({ chart, element }) {
      console.log(' ');
    },
    datasets: [],
  };
  if (typeof { results } !== 'undefined') {
    //console.log('test0', { results });
    var scores = [1, 2, 3];
    var marge=0;
    var kreditvolumen=0;
    var kundenValues=[0,0,0]
    for (let i = 0; i < results.length; i++) {
        for (let r=0; r< results[i]["ratings"].length; r++){
            //console.log(results[i]["ratings"][r]["rating"]["criterion"])
            if (results[i]["ratings"][r]["rating"]["criterion"]=="Marge"){
                marge=parseFloat(results[i]["ratings"][r]["answer"]);
                //console.log(marge)
            }
            if (results[i]["ratings"][r]["rating"]["criterion"]=="Kreditvolumen im Bestand"){
                kreditvolumen=parseFloat(results[i]["ratings"][r]["answer"]);
                //console.log(kreditvolumen)
            }

        }
      var complexity = 0;

      var values = results[i]['scores'];
      //console.log(values, "scores")
      var total = 0;
      for (let j = 0; j < values.length; j++) {
        total = total + parseFloat(values[j]['count']);
        complexity = complexity + parseFloat(values[j]['count']) * scores[j];
      }
      //console.log(values, complexity, i , "complexity")
      if (total === 0) {
        complexity = 1;
      } else {
        complexity = complexity / total;
      }
      complexity = parseFloat(results[i]['ratings'][2]['answer']) / complexity;

      data['datasets'][i] = {
        label: results[i]['productName'],
        data: [
          {
            y: marge,
            x: complexity,
            r: kreditvolumen
          },
        ],
        backgroundColor: colors[i % colors.length],
      };
    }
  }
  //console.log('test1', { results });
  return (
    <div>
      <Card alignItems="center" bg="gray.100">
        <Flex
          flexDirection="column"
          w="full"
          gridGap={1}
          justifyContent="space-between"
          alignItems="stretch"
        >
          <BubbleGraph data={data}></BubbleGraph>
          <ShowPieCharts results={results} />
        </Flex>
      </Card>
      <Heading>Legend</Heading>

      <Card alignItems="center" bg="gray.600">
        <Flex
          flexDirection="column"
          w="full"
          gridGap={1}
          justifyContent="space-between"
          alignItems="stretch"
        >
          <Text>Bubble Size = Volume in EUR </Text>

          <Flex
            flexDirection="row"
            w="full"
            gridGap={1}
            justifyContent="space-between"
            alignItems="stretch"
          >
            <Flex w="30%">
              <CircleIcon boxSize={8} color={green}></CircleIcon> = simple
            </Flex>
            <Flex w="30%">
              <CircleIcon boxSize={8} color={yellow}></CircleIcon> = medium
            </Flex>
            <Flex w="30%">
              <CircleIcon boxSize={8} color={red}></CircleIcon> = complex
            </Flex>
          </Flex>
        </Flex>
      </Card>
    </div>
  );
}

export default Figure;
