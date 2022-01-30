import {
  Input,
  Button,
  CircularProgress,
  Spacer,
  Textarea,
  HStack,
  VStack,
  Flex,
  List,
  Link,
  Collapse,
  useDisclosure,
} from '@chakra-ui/react';

import { TriangleUpIcon, TriangleDownIcon } from '@chakra-ui/icons';

import AddProductButton from './AddProductButton';
import React from 'react';
import Card from '../../components/Card';
import { useStoreActions, useStoreState } from 'easy-peasy';
import ProductVariant from './ProductVariant';

export default function ProductRow({ product, editMode }) {
  const { isOpen, onToggle } = useDisclosure();

  const updateProductName = useStoreActions((actions) => actions.productList.updateProductName);
  const updateProduct = useStoreActions((actions) => actions.productList.updateProduct);
  const updateProductComment = useStoreActions((actions) => actions.productList.updateProductComment  );

  const getVariants = useStoreState((state) => state.productList.getVariants);
  const productVariants = getVariants(product);

  const setName = (newName) => {
    setValidName(newName!=='')
    updateProductName({productID: product.productID, newName});

  };
  const setComment = (newComment) => {
    updateProductComment({productID: product.productID, newComment});
  };


  return (
    <Card
      layerStyle="card_bordered"
      justifyContent="space-between"
      direction="column"
      pb={5}
      // w={(parentID > 0) ? ' 90%' : 'full'}
      _hover={{ boxShadow: '2xl' }}
    >
      <Flex direction="column" w="full" justifyContent="space-between">
        <Input
          mb={1}
          variant="bold"
          align="center"
          size="2xl"
          isDisabled={!editMode}
          onChange={(e) => {
            setName(e.target.value);
          }}
          value={product.productName}
        />
        <Flex w="full" mb={3}>
          <Spacer />

          <VStack>
            <CircularProgress size="40px" value={product.progressEconomic} />

            <Link href="/ratings">
              <Button variant="whisper">Economical</Button>
            </Link>
          </VStack>

          <VStack ml="5%">
            <CircularProgress size="40px" value={product.progressComplexity} />
            <Link href="/ratings">
              <Button variant="whisper" href="/ratings">
                Complexity
              </Button>
            </Link>
          </VStack>

          <Spacer />
          <Textarea
            width="50%"
            isReadOnly={!editMode}
            value={product.comment !== null ? product.comment : ''}
            onChange={(e) => {
              setComment(e.target.value);
            }}
            placeholder="Anmerkung"
          />
        </Flex>

        <Flex w="full">
          {editMode ? <AddProductButton parentProductID={product.productID} mr={2} /> : undefined}
          <Button
            variant="link"
            size="lg"
            shadow={0}
            onClick={onToggle}
            rightIcon={isOpen ? <TriangleUpIcon /> : <TriangleDownIcon />}
            px={5}
          >
            Variants ({productVariants.length})
          </Button>

          <Spacer />

          <Button variant="whisper">Upload Reference</Button>
        </Flex>
      </Flex>
      <Flex w="full">
        <Collapse in={isOpen} w="100%" animateOpacity style={{ width: '100%' }}>
          <Flex w="full" mt={5}>
            <List w="full">
              {productVariants.map((variant) => (
                // <p>{JSON.stringify(variant)}</p>
                <ProductVariant product={variant} editMode={editMode} key={variant.productID} />
              ))}
            </List>
          </Flex>
        </Collapse>
      </Flex>
    </Card>
  );
}
