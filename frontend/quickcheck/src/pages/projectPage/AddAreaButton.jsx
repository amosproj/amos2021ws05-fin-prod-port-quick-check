import { React, useState, useEffect } from 'react';
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
  IconButton,
  Select,
} from '@chakra-ui/react';
import { AddIcon } from '@chakra-ui/icons';
import { useStoreState, useStoreActions } from 'easy-peasy';

import { api } from '../../utils/apiClient.js';

export default function AddAreaButton(buttonProps) {
  const productAreas = useStoreState((state) => state.project.data.productAreas);
  const addProductArea = useStoreActions((actions) => actions.project.addProductArea);
  const { isOpen, onOpen, onClose } = useDisclosure();
  const [allAreas, setAllAreas] = useState([])
  const [selectedArea, setSelectedArea] = useState();
  
  useEffect(() => {
    api.url('/productareas').get().json(setAllAreas);
  }, [])

  const getAreaByID = (areaID) => {
    const index = allAreas.map((a) => a.id).indexOf(areaID);
    console.log({index, areaID, allAreas})
    return allAreas[index];
  }

  return (
    <>
      <IconButton
        icon={<AddIcon />}
        aria-label="Add Product Area"
        onClick={onOpen}
        {...buttonProps}
      ></IconButton>

      <Modal isOpen={isOpen} onClose={onClose}>
        <ModalOverlay />
        <ModalContent>
          <ModalHeader color="primary">Add Product Area</ModalHeader>
          <ModalCloseButton />

          <ModalBody px={10}>
            <Select isRequired placeholder="Select Poduct Area..." onChange={e => setSelectedArea(parseInt(e.target.value))}
            >
              {
                allAreas
                  .filter((area) => !productAreas.map((a) => a.id).includes(area.id))
                  .map((area) =>(
                    <option value={area.id} key={area.id} >{area.name} ({area.category})</option>
                  ))
              }
            </Select>
          </ModalBody>

          <ModalFooter>
            <Button
              variant="primary"
              mr={3}
              disabled={selectedArea === undefined}
              onClick={(e) => {
                addProductArea(getAreaByID(selectedArea));
                console.log(selectedArea);
                onClose();
                console.log(productAreas);
              }}
            >
              Save
            </Button>
            <Button onClick={onClose} variant="whisper">
              Cancel
            </Button>
          </ModalFooter>
        </ModalContent>
      </Modal>
    </>
  );
}
