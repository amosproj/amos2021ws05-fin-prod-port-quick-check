import { React } from 'react';
import { Button, HStack, Heading, Spacer } from '@chakra-ui/react';

export default function ProductArea({ productArea, RemoveProjectAreaButton }) {
  return (
    <>
      <HStack spacing={3}>
        <Button as="a" variant="secondary" rounded="3xl" href="/projects" w="full" py={9}>
          <Spacer />
          <Heading size="lg" align="center">
            {productArea.name}
          </Heading>
          <Spacer />
        </Button>
        {RemoveProjectAreaButton}
      </HStack>
    </>
  );
}
