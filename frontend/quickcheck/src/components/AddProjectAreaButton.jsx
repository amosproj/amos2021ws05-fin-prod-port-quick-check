import { React, useState } from 'react';
import {
  useDisclosure,
  Modal,
  ModalOverlay,
  ModalContent,
  ModalBody,
  ModalFooter,
  ModalCloseButton,
  ModalHeader,
  Button,
  IconButton
} from '@chakra-ui/react';
import { AddIcon } from '@chakra-ui/icons';

//components
import { Selection } from './Inputs.jsx';


import {FetchAllAreas} from "./FetchAllAreas.jsx"

export default function AddProjectAreaButton({ onAdd }) {
  const { isOpen, onOpen, onClose } = useDisclosure();
  const allAreas = FetchAllAreas();

  const [selectedArea, setSelectedArea] = useState();

  const header = 'Add Product Area';

  const getAreaFromName = (areaName) => {
    return allAreas.filter((m) => m.name === areaName)[0];
  };

  return (
    <>
      <IconButton
        icon={<AddIcon />}
        aria-label="Add Product Area"
        onClick={onOpen}
        variant="primary"
        size="lg"
        w={16}
      ></IconButton>

      <Modal isOpen={isOpen} onClose={onClose}>
        <ModalOverlay />
        <ModalContent>
          <ModalHeader color="primary">{header}</ModalHeader>
          <ModalCloseButton />

          <ModalBody px={10}>
            <Selection
              placeholder="Select Poduct Area..."
              options={allAreas.map((e) => e.name)}
              onChange={setSelectedArea}
            />
          </ModalBody>

          <ModalFooter>
            <Button
              variant="primary"
              mr={3}
              onClick={(e) => {
                onAdd(getAreaFromName(selectedArea).id);
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
