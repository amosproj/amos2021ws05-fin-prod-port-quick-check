import { Box, Input, Wrap, Flex, Heading, Button, Text, useColorModeValue } from '@chakra-ui/react';
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
      <Flex w="50%" direction="row" justifyContent="center">
        <Button2 />
        <Button2 />
        <Button2 />
      </Flex>

      <Flex
        border="2px"
        borderColor="black"
        borderRadius="md"
        direction="column"
        justifyContent="center"
        p={16}
      >
        <Button2 />
        <Button2 />
        <Button2 />
      </Flex>

      <Flex variant="brand" p="2">
        <Button variant="secondary">fii</Button>
        <Button2 />
        <Button2 />
      </Flex>
    </Page>
  );
}
