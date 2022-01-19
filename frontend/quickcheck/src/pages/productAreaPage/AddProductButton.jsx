import React, { useState, useEffect } from 'react';
import Page from '../../components/Page';
import { useStoreState, useStoreActions } from 'easy-peasy';
import { useParams } from 'react-router-dom';
import {
  Modal,
  ModalOverlay,
  ModalHeader,
  List,
  ModalContent,
  ModalBody,
  FormControl,
  ModalFooter,
  ModalCloseButton,
  Button,
  useDisclosure,
  HStack,
  IconButton,
  Input,
} from '@chakra-ui/react';
import { AddIcon } from '@chakra-ui/icons';


export default function AddProductButton({ onAddProduct }) {
    const { isOpen, onOpen, onClose } = useDisclosure();
    const [productName, setProductName] = useState('');
    const header = 'Add Product';
    return (
      <>
        <IconButton icon={<AddIcon />} variant="primary" size="lg" onClick={onOpen} />
        <Modal isOpen={isOpen} onClose={onClose}>
          <ModalOverlay />
          <ModalContent>
            <ModalHeader color="primary">{header}</ModalHeader>
            <ModalCloseButton />
            <ModalBody px={10}>
              <FormControl>
                <Input
                  mb={6}
                  placeholder="Product Name"
                  onChange={(e) => setProductName(e.target.value)}
                />
              </FormControl>
            </ModalBody>
            <ModalFooter py={5} px={10}>
              <Button
                variant="primary"
                mx={3}
                onClick={(e) => {
                  onAddProduct(productName);
                  onClose();
                }}
              >
                Save
              </Button>
              <Button onClick={onClose} variant="wisper">
                Cancel
              </Button>
            </ModalFooter>
          </ModalContent>
        </Modal>
      </>
    );
  }