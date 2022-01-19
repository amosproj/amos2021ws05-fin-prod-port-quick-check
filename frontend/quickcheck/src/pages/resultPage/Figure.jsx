import React from 'react';
import { Heading, Flex, Icon, Text } from '@chakra-ui/react';

import Card from '../../components/Card';
import PieChartGraph from './PieChart';
import BubbleGraph from './BubbleChart';

const testdata = [0, 3, 2];
const testdata2 = [3, 5, 4];
const testdata3 = [1, 2, 3];
const testdata4 = [3, 2, 1];
  var colors = ['rgba(255,72,166)', 'rgba(147,213,34)', 'rgba(41,213,255)', 'rgba(82,8,129)'];
var yellow = 'rgba(255, 195, 0, .7)';
var green = 'rgba(131, 239, 56, .7 )';
var red = 'rgba(239, 65, 56, .7 )';

const CircleIcon = (props) => (
  <Icon viewBox="10 1 200 200" {...props}>
    <path fill={props.color} d="m 100, 100 m -75, 0 a 75,75 0 1,0 150,0 a 75,75 0 1,0 -150,0" />
  </Icon>
);

function ShowPieCharts({ results }) {
var rows=[];
for (let i = 0; i < results.length/4 ; i++) {
    rows[i]=[];
    for (let j= 0 ;(j<4) && ( i*4 + j<results.length); j++){
        var index=i*4+ j;
        rows[i][j]=[];
        rows[i][j][0]=colors[index%colors.length];
        rows[i][j][1]=results[index]['productName'];
        rows[i][j][2]=colors[index];
        rows[i][j][3]=testdata3; //TODO add backend input
        rows[i][j][4]=testdata4;
    }
}


return (<Flex
  flexDirection="column"
  w="full"
  gridGap={1}
  justifyContent="space-between"
  alignItems="stretch"
>
{rows.map((row) => (
  <PieChartRow
    row_data={row}
  ></PieChartRow>
))}

    </Flex>);
}

function PieChartRow({row_data}){

  return (
    <Flex
      flexDirection="row"
      w="full"
      gridGap={1}
      justifyContent="space-between"
      alignItems="stretch"
    >
    {row_data.map((pie) => (
        <Flex w="25%">
          <PieChartGraph
            data_outer={pie[3]}
            data_inner={pie[4]}
            color={pie[0]}
            title={pie[1]}
          ></PieChartGraph>
        </Flex>
    ))}
    </Flex>
  );
}

function Figure({ results }) {

  var scores = [1, 2, 3]; //TODO are these the right values?
  var data = {
    click: function ({ chart, element }) {
      console.log('Box annotation clicked');
    },
    datasets: [],
  };

  for (let i = 0; i < results.length; i++) {
    var complexity = 0;
    var values = results[i]['ratings'][2]['answer'].split(' ');
    for (let j = 0; j < values.length; j++) {
      complexity = complexity + parseFloat(values[j]) * scores[j];
    }
    complexity = complexity / 100;
    data['datasets'][i] = {
      label: results[i]['productName'],
      data: [
        {
          x: parseFloat(results[i]['ratings'][1]['answer']),
          y: complexity,
          r: parseFloat(results[i]['ratings'][0]['answer']) / 10,
        },
      ],
      backgroundColor: colors[i % colors.length],
    };
    console.log(results[i], data['datasets'][i]);
  }

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
