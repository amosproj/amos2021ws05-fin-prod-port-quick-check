import { Box, Flex, Heading, Button, Text, useColorModeValue } from '@chakra-ui/react';
import Card from '../components/Card';

import Page from '../components/Page';

export const Button2 = () => {
  return (
    <Button variant="card" w="100px">
      button 2
    </Button>
  );
};

export default function TestRange() {
  const headingColor = useColorModeValue('blue', 'red');

  return (
    <>
      <Page>
        <Box
          align="center"
          w="80%"
          h="80%"
          rounded="2xl"
          bg="bg.300"
          border="10px"
          borderWidth="10px"
          borderColor="red"
        >
          <Button rounded="3xl" m={3} colorScheme="brand">
            button uses custom color, colorScheme is automatic but only works for buttons
          </Button>
          <Heading color={headingColor}>switching color for dark and bright</Heading>
          <Box border="1px" borderColor="blue">
            Card
          </Box>
        </Box>
        <Card variant="brand"></Card>

        <Flex variant="brand" p="2">
          <Button size="lg" variant="primary">
            click me
          </Button>
          <Button shadow="outline" size="lg" variant="primary">
            click me
          </Button>
          <Button isLoading size="lg" variant="primary">
            click me
          </Button>
          <Button disabled size="lg" variant="primary">
            click me
          </Button>
        </Flex>

        <Flex variant="brand" p="2">
          <Button size="lg" variant="secondary">
            click me
          </Button>
          <Button shadow="outline" size="lg" variant="secondary">
            click me
          </Button>
          <Button isLoading size="lg" variant="secondary">
            click me
          </Button>
          <Button disabled size="lg" variant="secondary">
            click me
          </Button>
        </Flex>

        <Flex variant="brand" p="2">
          <Button size="lg" variant="wisper">
            click me
          </Button>
          <Button shadow="outline" focus size="lg" variant="wisper">
            click me
          </Button>
          <Button isLoading size="lg" variant="wisper">
            click me
          </Button>
          <Button disabled size="lg" variant="wisper">
            click me
          </Button>
        </Flex>
      </Page>
      <Box bg="red" w="full" h={3} roundedTop="lg" m="0"></Box>
      <Flex bg="blue" w="full" h={20} roundedBottom="lg" m="0" align="center"></Flex>
    </>
  );
}
