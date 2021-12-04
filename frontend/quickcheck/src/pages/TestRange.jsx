import { Box, Input, Heading, Button, Text, useColorModeValue } from '@chakra-ui/react';
import Page from '../components/Page';

export default function TestRange() {

  const  headingColor  = useColorModeValue('blue', 'red')

  return (
    <Page>
    <Box align='center' w='20%' h='20%'>
      <Button rounded='3xl' m={3} colorScheme='brand'>button uses custom color, colorScheme is automatic but only works for buttons</Button>
      <Text variant='brand'>text field using custom variant</Text>
      <Heading color={headingColor}>switching color for dark and bright</Heading>
    </Box>
    </Page>
  );
}
