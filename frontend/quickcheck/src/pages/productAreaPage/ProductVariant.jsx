import { Input, Button, Spacer, Textarea, VStack, Flex, useColorModeValue } from '@chakra-ui/react';
import {React, useState} from 'react';
import Card from '../../components/Card';
import { useStoreActions } from 'easy-peasy';

export default function ProductVariant({ product, editMode }) {
  const bg = useColorModeValue('gray.100', 'gray.600');
  const [validName, setValidName] = useState(true);
  const updateProductName = useStoreActions((actions) => actions.productList.updateProductName);
  const updateProductComment = useStoreActions(
    (actions) => actions.productList.updateProductComment
  );

  const setName = (newName) => {
    setValidName(newName!=='')
    updateProductName({productID: product.productID, newName});
  };
  const setComment = (newComment) => {
    updateProductComment({productID: product.productID, newComment});
  };

  return (
    <Card
      bg={bg}
      w="full"
      m={2}
      justifyContent="space-between"
      direction="column"
      _hover={{ boxShadow: 'lg' }}
    >
      <Flex direction="row" w="full" mb={1}>
        <Input
          align="center"
          size="lg"
          variant="bold"
          w="50%"
          borderColor={'gray.500'}
          borderWidth={editMode ? 1 : 0}
          mr={1}
          isInvalid={!validName}
          isDisabled={!editMode}
          onChange={(e) => {
            setName(e.target.value);
          }}
          value={product.productName}
        />
        <Textarea
          w="50%"
          isReadOnly={!editMode}
          value={product.comment !== null ? product.comment : ''}
          onChange={(e) => {
            setComment(e.target.value);
          }}
          placeholder="Anmerkung"
        />
      </Flex>
      <Flex direction="row" w="full">
        <Flex w="50%">
          <Spacer />

          <Button variant="whisper">Economical</Button>
          <Spacer />

          <Button variant="whisper">Complexity</Button>
          <Spacer />
        </Flex>
        <Flex w="50%">
          <Spacer />
          <Button variant="whisper">Upload Reference</Button>
        </Flex>
      </Flex>
    </Card>
  );
}
