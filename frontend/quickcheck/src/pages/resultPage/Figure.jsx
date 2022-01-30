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

  var index = 0;
  for (let i = 0; i < results.length / 4; i++) {
    rows[i] = [];
    rows[i]['key'] = i;
    var kundenValues = [0, 0, 0];
    for (let j = 0; j < 4 && i * 4 + j < results.length; j++) {
      index = i * 4 + j;
      kundenValues = [0, 0, 0];
      var unfinishedFlag = 0;

      for (let r = 0; r < results[j]['ratings'].length; r++) {
        if (results[j]['ratings'][r]['answer'] == null) {
          unfinishedFlag = 1;
        } else if (results[j]['ratings'][r]['rating']['criterion'] == 'Kunde') {
          var kundenValuesTmp = results[j]['ratings'][r]['answer'].split(', ');
          for (let k = 0; k < kundenValuesTmp.length; k++) {
            kundenValues[k] = parseFloat(kundenValuesTmp[k]);
          }
          //console.log("kundenValues", kundenValues);
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
      var ratingValues = results[index]['counts']; //
      var adaptedRatingValues = [0, 0, 0];
      var ratingTotal = 0;
      rows[i][j] = [];

      for (let x = 0; x < ratingValues.length; x++) {
        //console.log("rating", ratingValues[x]['count'])
        ratingTotal = ratingTotal + ratingValues[x];
      }
      if (ratingTotal === 0) {
        adaptedRatingValues = [0];
      } else {
        for (let x = 0; x < ratingValues.length; x++) {
          adaptedRatingValues[x] = (parseFloat(ratingValues[x]) / ratingTotal) * 100;
        }
      }
      //console.log(kundenValues, j, index);
      rows[i][j]['key'] = { index };
      rows[i][j][0] = colors[index % colors.length];
      rows[i][j][1] = results[index]['productName'];
      rows[i][j][2] = kundenValues;
      rows[i][j][3] = adaptedRatingValues;
      if (unfinishedFlag == 1) {
        rows[i][j][4] = 'unfinished';
      } else {
        rows[i][j][4] = results[index]['counts'];
      }
    }
  }

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
  var maxbX = 0;
  var maxbY = 0;
  var minbX = 100;
  var minbY = 100;
  var maxR = 0;
  var data = {
    click: function ({ chart, element }) {
      console.log(' ');
    },
    datasets: [],
  };
  if (results !== null) {
    //console.log('test0', { results });
    var scores = [1, 2, 3];
    var marge = 0;

    var kreditradius = 0;
    var kreditvolumen = 0;
    var kundenValues = [0, 0, 0];
    for (let i = 0; i < results.length; i++) {
      for (let r = 0; r < results[i]['ratings'].length; r++) {
        //console.log(results[i]["ratings"][r]["rating"]["criterion"])
        if (results[i]['ratings'][r]['rating']['criterion'] == 'Marge') {
          marge = parseFloat(results[i]['ratings'][r]['answer']);
          //console.log(marge)
        }
        if (results[i]['ratings'][r]['rating']['criterion'] == 'Kreditvolumen im Bestand') {
          kreditvolumen = parseFloat(results[i]['ratings'][r]['answer']);
          kreditradius = kreditvolumen / 3.14;
          kreditradius = Math.sqrt(kreditradius);
          //kreditradius=kreditradius*10
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
      //complexity = parseFloat(results[i]['ratings'][2]['answer']) / complexity;
      complexity = kreditvolumen / complexity;
      if (marge > maxbY) {
        maxbY = marge;
      }
      if (marge < minbY) {
        minbY = marge;
      }
      if (complexity > maxbX) {
        maxbX = complexity;
      }
      if (complexity < minbX) {
        minbX = complexity;
        console.log('minbx', minbX);
      }
      if (kreditradius > maxR) {
        maxR = kreditradius;
      }
      data['datasets'][i] = {
        label: results[i]['productName'],
        data: [
          {
            y: marge,
            x: complexity,
            //r: kreditradius,
            r: kreditvolumen,
            volume: kreditvolumen,
          },
        ],
        backgroundColor: colors[i % colors.length],
      };
    }
  }
  maxbX = parseInt(maxbX + maxR);
  maxbY = parseInt(maxbY + maxR);
  minbX = parseInt(minbX - maxR);
  minbY = parseInt(minbY - maxR);
  minbY = 0;
  minbX = 0;
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
          <BubbleGraph
            data={data}
            minbX={minbX}
            maxbX={maxbX}
            minbY={minbY}
            maxbY={maxbY}
          ></BubbleGraph>
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
