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
import { AddIcon, DeleteIcon } from '@chakra-ui/icons';
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
        size="md"
        colorScheme="green"
        boxShadow={'2xl'}
        rounded={'md'}
        w="50px"
        p={3}
      ></IconButton>

      <Modal isOpen={isOpen} onClose={onClose}>
        <ModalOverlay />
        <ModalContent>
          <ModalHeader color="teal.300">{header}</ModalHeader>
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
              colorScheme="blue"
              mr={3}
              onClick={(e) => {
                onAdd(getAreaFromName(selectedArea).id);
                onClose();
              }}
            >
              Save
            </Button>
            <Button onClick={onClose}>Cancel</Button>
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
        <IconButton
          icon={<DeleteIcon />}
          onClick={onOpen}
          size="md"
          color="red.900"
          bg="red.400"
          isRound="true"
          // w={16}
        />
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
        <LinkBox as="button" w="full">
          <Flex
            bg="blue.700"
            w="full"
            rounded="lg"
            p={3}
            align="center"
            _hover={{ boxShadow: 'md', bg: 'blue.600' }}
            onClick={<Link to="/projects" />}
          >
            <Spacer />
            <LinkOverlay href="/projects"></LinkOverlay>
            <Heading size="md" align="center">
              {productArea.name}
            </Heading>

            <Spacer />
            <CircularProgress mx={3} value={50} color="pink.400">
              <CircularProgressLabel>{50}%</CircularProgressLabel>
            </CircularProgress>
          </Flex>
        </LinkBox>
        {removeButton}
      </HStack>
    </>
  );
}

const areaMock = {
  0: { id: 0, name: 'Kredit (p)', category: 'Privat' },
  1: { id: 1, name: 'Kredit (b)', category: 'Business' },
  2: { id: 2, name: 'Kunden (p)', category: 'Privat' },
  3: { id: 3, name: 'Kunden (b)', category: 'Business' },
};

const fetchAllAreas = () => {
  return Object.values(areaMock);
};

export default function ProductAreaList({ editable, areaIDs, handleChange }) {
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
      <List spacing={3} w="50%">
        {areaIDs.map((id) => (
          <ProductArea
            productArea={fetchArea(id)}
            removeButton={editable ? <RemoveButton onRemove={handleRemoveArea(id)} /> : <div />}
          />
        ))}
      </List>

      {editable ? <AddButton onAdd={handleAddArea}></AddButton> : <div />}
    </>
  );
}
