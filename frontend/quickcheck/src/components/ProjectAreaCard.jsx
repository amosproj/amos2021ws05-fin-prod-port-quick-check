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
  Flex,
  Popover,
  PopoverTrigger,
  PopoverContent,
  PopoverHeader,
  PopoverBody,
  HStack,
  LinkOverlay,
  Heading,
  Link,
  IconButton,
  LinkBox,
  Spacer,
  CircularProgress,
  CircularProgressLabel,
  List,
} from '@chakra-ui/react';
import { AddIcon, DeleteIcon, ArrowForwardIcon } from '@chakra-ui/icons';
import { Selection } from './Inputs.jsx';

function AddButton({ onAdd }) {
  const { isOpen, onOpen, onClose } = useDisclosure();
  const allAreas = fetchAllAreas();

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

function RemoveButton({ onRemove }) {
  const { onOpen, onClose, isOpen } = useDisclosure();
  return (
    <Popover isOpen={isOpen} onOpen={onOpen} onClose={onClose} isLazy={true} w="wrap">
      <PopoverTrigger>
        <IconButton icon={<DeleteIcon />} onClick={onOpen} variant="wisper" size="md" />
      </PopoverTrigger>
      <PopoverContent>
        <PopoverHeader fontWeight="semibold">Confirm removing this User</PopoverHeader>
        <PopoverBody>
          <Button
            colorScheme="red"
            mx={1}
            onClick={(e) => {
              onRemove();
              onClose();
            }}
          >
            Remove
          </Button>
          <Button mx={1} onClick={onClose}>
            Cancel
          </Button>
        </PopoverBody>
      </PopoverContent>
    </Popover>
  );
}

function ProductArea({ productArea, removeButton }) {
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
        {removeButton}
      </HStack>
    </>
  );
}

const areaMock = {
  0: { id: 0, name: 'Kredit', category: 'Privat' },
  1: { id: 1, name: 'Kunden', category: 'Business' },
  2: { id: 2, name: 'Payments', category: 'Privat' },
};

const fetchAllAreas = () => {
  return Object.values(areaMock);
};

export default function ProductAreaList({ editMode, areaIDs, handleChange }) {
  const fetchArea = (areaID) => {
    return areaMock[areaID];
  };

  const handleAddArea = (newID) => {
    handleChange([...areaIDs, newID]);
  };

  const handleRemoveArea = (removeID) => () => {
    const updatedAreaIDs = areaIDs.filter((m) => m !== removeID);
    handleChange(updatedAreaIDs);
  };

  return (
    <>
      <List w="50%" align="center" spacing={4} pb={5}>
        {areaIDs.map((id) => (
          <ProductArea
            key={id}
            productArea={fetchArea(id)}
            removeButton={editMode ? <RemoveButton onRemove={handleRemoveArea(id)} /> : <div />}
          />
        ))}
        {editMode ? <AddButton onAdd={handleAddArea}></AddButton> : <div />}
      </List>
    </>
  );
}
