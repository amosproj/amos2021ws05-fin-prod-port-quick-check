import { Input, Button, Spacer, Textarea, VStack, Flex } from '@chakra-ui/react';
import React from 'react';
import Card from '../../components/Card';
import { useStoreActions } from 'easy-peasy';

export default function ProductVariant({ product, editMode }) {
  const changeProductName = useStoreActions((actions) => actions.productList.changeProductName);
  const changeProductComment = useStoreActions(
    (actions) => actions.productList.changeProductComment
  );

  const setProduct = (productName) => {
    product.productName = productName;
    changeProductName(product);
  };
  const handleTextInputChange = (comment) => {
    product.comment = comment;
    changeProductComment(product);
  };

  return (
    <Card bg="gray.600" w="full" m={2} justifyContent="space-between" direction="column">
      <Flex direction="row" w="full" mb={1}>
        <Input
          align="center"
          size="lg"
          w="50%"
          isDisabled={!editMode}
          onChange={(e) => {
            setProduct(e.target.value);
          }}
          value={product.productName}
        />
        <Textarea
          w="50%"
          isReadOnly={!editMode}
          value={product.comment !== null ? product.comment : ''}
          onChange={(e) => {
            handleTextInputChange(e.target.value);
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
