import React from 'react';
import {
  Heading,
  Flex,
  Icon,
} from '@chakra-ui/react';

import Card from '../../components/Card';
import PieChartGraph from './PieChart';
import BubbleGraph from './BubbleChart';

const testdata=[0,3,2]
const testdata2= [3,5,4]
const testdata3= [1,2,3]
const testdata4= [3,2,1]

var yellow='rgba(255, 195, 0, .7)';
var green= 'rgba(131, 239, 56, .7 )';
var red ='rgba(239, 65, 56, .7 )';


const data = {
    click: function({chart, element}) {
      console.log('Box annotation clicked');
  },

  datasets: [{
    label: "Car Leasing",
    data: [{
      x: 150,
      y: -150,
      r: 20
    }],
    backgroundColor: 'rgba(255,72,166)'
},
{
  label: 'Consumer Credit',
  data: [{
    x: -140,
    y: 100,
    r: 30}
],
  backgroundColor: 'rgba(147,213,34)'
},{
label: 'Overdraft Facility',
data: [{
  x: -60,
  y: 30,
  r: 60}
],
backgroundColor: 'rgba(41,213,255)'
},{
label: 'Mortgage',
data: [{
  x: 10,
  y: 0,
  r: 70}
],
backgroundColor: 'rgba(82,8,129)'
}]
};

const CircleIcon = (props) => (
  <Icon viewBox='10 1 200 200' {...props}>
    <path
      fill={props.color}
      d='m 100, 100 m -75, 0 a 75,75 0 1,0 150,0 a 75,75 0 1,0 -150,0'
    />
  </Icon>
)

function Figure(props) {
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
          <Flex
            flexDirection="row"
            w="full"
            gridGap={1}
            justifyContent="space-between"
            alignItems="stretch"
          >
          <Flex
            w="25%"
          >
        <PieChartGraph
         data_outer={testdata} data_inner={testdata3} color= 'rgba(255,72,166)'
        title={["Car" ,"Leasing" ]}></PieChartGraph>
          </Flex>
          <Flex
            w="25%"
          >
             <PieChartGraph data_outer={testdata2} data_inner={testdata} color='rgba(147,213,34)'
             title={["Consumer",  'Credit']}></PieChartGraph>
          </Flex>
          <Flex
            w="25%"
          >
            <PieChartGraph  data_outer={testdata3} data_inner={testdata4} color='rgba(41,213,255)'
            title={["Overdraft", "Facility"]}></PieChartGraph>
          </Flex>
          <Flex
            w="25%"
          >
            <PieChartGraph  data_outer={testdata4} data_inner={testdata2} color='rgba(82,8,129)'
            title={["Mortgage", ""]}></PieChartGraph>
          </Flex>
              </Flex>

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
<text>Bubble Size = Volume in EUR </text>

<Flex
  flexDirection="row"
  w="full"
  gridGap={1}
  justifyContent="space-between"
  alignItems="stretch"
>
<Flex
  w="30%"
>
<CircleIcon  boxSize={8} color={green} ></CircleIcon> = simple
</Flex>
<Flex
  w="30%"
>
<CircleIcon  boxSize={8} color={yellow} ></CircleIcon> = medium
</Flex>
<Flex
  w="30%"
>
<CircleIcon boxSize={8} color={red} ></CircleIcon> = complex
</Flex>
</Flex>
</Flex>
</Card>
          </div>)
}

export default Figure;
