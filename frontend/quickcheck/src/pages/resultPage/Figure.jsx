import React from 'react';
import { Heading, Flex, Icon, Text } from '@chakra-ui/react';

import Card from '../../components/Card';
import PieChartGraph from './PieChart';
import BubbleGraph from './BubbleChart';

const testdata = [0, 3, 2];
const testdata2 = [3, 5, 4];
const testdata3 = [1, 2, 3];
const testdata4 = [3, 2, 1];

var yellow = 'rgba(255, 195, 0, .7)';
var green = 'rgba(131, 239, 56, .7 )';
var red = 'rgba(239, 65, 56, .7 )';

const CircleIcon = (props) => (
  <Icon viewBox="10 1 200 200" {...props}>
    <path fill={props.color} d="m 100, 100 m -75, 0 a 75,75 0 1,0 150,0 a 75,75 0 1,0 -150,0" />
  </Icon>
);

function ShowPieCharts({ results }) {
  return (
    <Flex
      flexDirection="row"
      w="full"
      gridGap={1}
      justifyContent="space-between"
      alignItems="stretch"
    >
      <Flex w="25%">
        <PieChartGraph
          data_outer={testdata}
          data_inner={testdata3}
          color="rgba(255,72,166)"
          title={['Car', 'Leasing']}
        ></PieChartGraph>
      </Flex>
      <Flex w="25%">
        <PieChartGraph
          data_outer={testdata2}
          data_inner={testdata}
          color="rgba(147,213,34)"
          title={['Consumer', 'Credit']}
        ></PieChartGraph>
      </Flex>
      <Flex w="25%">
        <PieChartGraph
          data_outer={testdata3}
          data_inner={testdata4}
          color="rgba(41,213,255)"
          title={['Overdraft', 'Facility']}
        ></PieChartGraph>
      </Flex>
      <Flex w="25%">
        <PieChartGraph
          data_outer={testdata4}
          data_inner={testdata2}
          color="rgba(82,8,129)"
          title={['Mortgage', '']}
        ></PieChartGraph>
      </Flex>
    </Flex>
  );
}

function Figure({ results }) {
  var colors = ['rgba(255,72,166)', 'rgba(147,213,34)', 'rgba(41,213,255)', 'rgba(82,8,129)'];
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
